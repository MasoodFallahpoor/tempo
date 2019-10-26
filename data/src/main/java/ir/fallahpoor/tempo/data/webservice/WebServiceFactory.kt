package ir.fallahpoor.tempo.data.webservice

import android.util.Base64
import ir.fallahpoor.tempo.data.common.PreferencesManager
import ir.fallahpoor.tempo.data.entity.AccessTokenEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WebServiceFactory @Inject constructor(private val preferencesManager: PreferencesManager) {

    private companion object {
        const val CLIENT_ID = "b654b2a549724fc69136b1729ce90faa"
        const val CLIENT_SECRET = "f05b62a9695e4510a582231caba66926"
        const val API_BASE_URL = "https://api.spotify.com/v1/"
        const val AUTHENTICATION_BASE_URL = "https://accounts.spotify.com/api/"
        const val HEADER_NAME_AUTHORIZATION = "Authorization"
    }

    private val retrofitApi = Retrofit.Builder()
        .baseUrl(API_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(getApiOkHttpClient())
        .build()

    private fun getApiOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(AddHeadersInterceptor())
            .authenticator(Authenticator())
            .build()

    private val retrofitAuthentication = Retrofit.Builder()
        .baseUrl(AUTHENTICATION_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(getAuthenticationOkHttpClient())
        .build()

    private fun getAuthenticationOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(AddAuthenticationHeaderInterceptor())
            .build()

    fun <S> createApiService(serviceClass: Class<S>): S =
        retrofitApi.create(serviceClass)

    fun <S> createAuthenticationService(serviceClass: Class<S>): S =
        retrofitAuthentication.create(serviceClass)

    private inner class AddHeadersInterceptor : Interceptor {

        override fun intercept(chain: Interceptor.Chain): Response {

            val original = chain.request()

            val request = original.newBuilder()
                .header(
                    HEADER_NAME_AUTHORIZATION,
                    "Bearer ${preferencesManager.getAccessToken()}"
                )
                .method(original.method(), original.body())
                .build()

            return chain.proceed(request)

        }

    }

    private inner class Authenticator : okhttp3.Authenticator {

        override fun authenticate(route: Route?, response: Response): Request? {

            runBlocking {
                val accessTokenResponse: retrofit2.Response<AccessTokenEntity> = getAccessToken()
                val newAccessToken: String? =
                    if (response.isSuccessful) {
                        accessTokenResponse.body()?.accessToken
                    } else {
                        null
                    }
                preferencesManager.setAccessToken(newAccessToken)
            }

            return response.request().newBuilder()
                .header(
                    HEADER_NAME_AUTHORIZATION,
                    "Bearer ${preferencesManager.getAccessToken()}"
                )
                .build()

        }

        private suspend fun getAccessToken(): retrofit2.Response<AccessTokenEntity> {
            val accessTokenWebService =
                createAuthenticationService(AccessTokenWebService::class.java)
            return withContext(Dispatchers.IO) {
                accessTokenWebService.getAccessToken("client_credentials")
            }
        }

    }

    private inner class AddAuthenticationHeaderInterceptor : Interceptor {

        override fun intercept(chain: Interceptor.Chain): Response {
            val original = chain.request()
            val request = original.newBuilder()
                .header(
                    HEADER_NAME_AUTHORIZATION,
                    "Basic " + Base64.encodeToString(
                        "$CLIENT_ID:$CLIENT_SECRET".toByteArray(),
                        Base64.NO_WRAP
                    )
                )
                .method(original.method(), original.body())
                .build()
            return chain.proceed(request)
        }

    }

}