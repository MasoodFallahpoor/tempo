package ir.fallahpoor.tempo.data.repository.authentication

import io.reactivex.Completable
import ir.fallahpoor.tempo.data.common.PreferencesManager
import ir.fallahpoor.tempo.data.entity.AccessTokenEntity
import ir.fallahpoor.tempo.data.webservice.AccessTokenWebService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthenticationRepositoryImpl(
    private val accessTokenWebService: AccessTokenWebService,
    private val preferencesManager: PreferencesManager
) : AuthenticationRepository {

    override fun getAccessToken() =

        Completable.create {

            if (accessTokenExists()) {
                it.onComplete()
            } else {
                val call: Call<AccessTokenEntity> =
                    accessTokenWebService.getAccessToken("client_credentials")
                call.enqueue(object : Callback<AccessTokenEntity> {
                    override fun onResponse(
                        call: Call<AccessTokenEntity>,
                        response: Response<AccessTokenEntity>
                    ) {
                        val accessToken: String? = response.body()?.accessToken
                        preferencesManager.setAccessToken(accessToken)
                        it.onComplete()
                    }

                    override fun onFailure(call: Call<AccessTokenEntity>, t: Throwable) {
                        it.onError(t)
                    }
                })
            }

        }

    private fun accessTokenExists() = preferencesManager.getAccessToken() != null

}