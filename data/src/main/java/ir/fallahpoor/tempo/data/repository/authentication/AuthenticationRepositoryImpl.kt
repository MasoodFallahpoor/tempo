package ir.fallahpoor.tempo.data.repository.authentication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ir.fallahpoor.tempo.data.common.ExceptionHumanizer
import ir.fallahpoor.tempo.data.common.PreferencesManager
import ir.fallahpoor.tempo.data.common.Resource
import ir.fallahpoor.tempo.data.entity.AccessTokenEntity
import ir.fallahpoor.tempo.data.webservice.AccessTokenWebService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Response

class AuthenticationRepositoryImpl(
    private val accessTokenWebService: AccessTokenWebService,
    private val preferencesManager: PreferencesManager
) : AuthenticationRepository {

    private var job: Job? = null

    override fun getAccessToken(): LiveData<Resource<Unit>> {

        val liveData = MutableLiveData<Resource<Unit>>()

        if (accessTokenExists()) {
            // When access token exists, there is no need to do anything. Just return
            // a successful result
            liveData.value = Resource.Success(Unit)
        } else {
            // When access token doesn't exist, make a web service call to obtain a
            // access token
            job = CoroutineScope(Dispatchers.IO).launch {

                val resource: Resource<Unit> =
                    try {
                        val response: Response<AccessTokenEntity> =
                            accessTokenWebService.getAccessToken("client_credentials")
                        if (response.isSuccessful) {
                            val accessToken: String? = response.body()?.accessToken
                            preferencesManager.setAccessToken(accessToken)
                            Resource.Success(Unit)
                        } else {
                            Resource.Error(response.message())
                        }
                    } catch (t: Throwable) {
                        Resource.Error(ExceptionHumanizer.getHumanizedErrorMessage(t))
                    }

                liveData.postValue(resource)

            }

        }

        return liveData

    }

    override fun dispose() {
        job?.cancel()
    }

    private fun accessTokenExists() = preferencesManager.getAccessToken() != null

}