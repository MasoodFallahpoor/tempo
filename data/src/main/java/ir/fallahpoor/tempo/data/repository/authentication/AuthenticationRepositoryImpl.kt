package ir.fallahpoor.tempo.data.repository.authentication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ir.fallahpoor.tempo.data.Error
import ir.fallahpoor.tempo.data.PreferencesManager
import ir.fallahpoor.tempo.data.Resource
import ir.fallahpoor.tempo.data.entity.AccessTokenEntity
import ir.fallahpoor.tempo.data.webservice.AccessTokenWebService
import ir.fallahpoor.tempo.data.webservice.WebServiceFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthenticationRepositoryImpl(
    private val webServiceFactory: WebServiceFactory,
    private val preferencesManager: PreferencesManager
) : AuthenticationRepository {

    override fun getAccessToken(): LiveData<Resource<Unit>> {

        val liveData = MutableLiveData<Resource<Unit>>()

        if (accessTokenExists()) {
            // When access token exists, there is no need to do anything. Just return
            // a successful result
            liveData.value = Resource(Resource.Status.SUCCESS, null, null)
        } else {
            // When access token doesn't exist, make a web service call to obtain a
            // access token
            val accessTokenWebService = webServiceFactory.createAuthenticationService(
                AccessTokenWebService::class.java
            )
            val accessTokenCall = accessTokenWebService.getAccessToken("client_credentials")

            accessTokenCall.enqueue(object : Callback<AccessTokenEntity> {

                override fun onFailure(call: Call<AccessTokenEntity>, t: Throwable) {
                    val error = Error(t.message!!)
                    liveData.value = Resource(Resource.Status.ERROR, null, error)
                }

                override fun onResponse(
                    call: Call<AccessTokenEntity>,
                    response: Response<AccessTokenEntity>
                ) {
                    if (response.isSuccessful) {
                        val accessToken: String? = response.body()?.accessToken
                        preferencesManager.setAccessToken(accessToken)
                        liveData.value = Resource(Resource.Status.SUCCESS, null, null)
                    } else {
                        val error = Error(response.message())
                        liveData.value = Resource(Resource.Status.ERROR, null, error)
                    }
                }

            })
        }

        return liveData

    }

    private fun accessTokenExists() = preferencesManager.getAccessToken() != null

}