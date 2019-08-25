package ir.fallahpoor.tempo.splash.di

import android.content.Context
import android.preference.PreferenceManager
import dagger.Module
import dagger.Provides
import ir.fallahpoor.tempo.data.PreferencesManager
import ir.fallahpoor.tempo.data.repository.authentication.AuthenticationRepository
import ir.fallahpoor.tempo.data.repository.authentication.AuthenticationRepositoryImpl
import ir.fallahpoor.tempo.data.webservice.AccessTokenWebService
import ir.fallahpoor.tempo.data.webservice.WebServiceFactory

@Module
class SplashModule(private val context: Context) {

    @Provides
    fun provideContext() = context

    @Provides
    internal fun provideAuthenticationRepository(
        accessTokenWebService: AccessTokenWebService,
        preferencesManager: PreferencesManager
    ): AuthenticationRepository {
        return AuthenticationRepositoryImpl(accessTokenWebService, preferencesManager)
    }

    @Provides
    internal fun provideSharedPreferences(context: Context) =
        PreferenceManager.getDefaultSharedPreferences(context)

    @Provides fun provideAccessTokenWebService(webServiceFactory: WebServiceFactory) : AccessTokenWebService =
        webServiceFactory.createAuthenticationService(AccessTokenWebService::class.java)

}