package ir.fallahpoor.tempo.data.repository.authentication

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import ir.fallahpoor.tempo.data.common.ExceptionHumanizer
import ir.fallahpoor.tempo.data.common.PreferencesManager
import ir.fallahpoor.tempo.data.common.Resource
import ir.fallahpoor.tempo.data.entity.AccessTokenEntity
import ir.fallahpoor.tempo.data.webservice.AccessTokenWebService
import kotlinx.coroutines.Dispatchers
import retrofit2.Response

class AuthenticationRepositoryImpl(
    private val accessTokenWebService: AccessTokenWebService,
    private val preferencesManager: PreferencesManager
) : AuthenticationRepository {

    override fun getAccessToken(): LiveData<Resource<Unit>> =
        liveData(Dispatchers.IO) {
            if (accessTokenExists()) {
                val resource: Resource<Unit> = Resource.Success(Unit)
                emit(resource)
            } else {
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
                emit(resource)
            }
        }

    private fun accessTokenExists() = preferencesManager.getAccessToken() != null

}