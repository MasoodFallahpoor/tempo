package ir.fallahpoor.tempo.splash.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import ir.fallahpoor.tempo.data.common.PreferencesManager
import ir.fallahpoor.tempo.data.repository.authentication.AuthenticationRepository
import ir.fallahpoor.tempo.data.repository.authentication.AuthenticationRepositoryImpl
import ir.fallahpoor.tempo.data.webservice.AccessTokenWebService
import ir.fallahpoor.tempo.data.webservice.WebServiceFactory

@InstallIn(ActivityComponent::class)
@Module
class SplashModule {
    @Provides
    fun provideAccessTokenWebService(webServiceFactory: WebServiceFactory): AccessTokenWebService {
        return webServiceFactory.createAuthenticationService(AccessTokenWebService::class.java)
    }

    @Provides
    fun provideAuthenticationRepository(
        accessTokenWebService: AccessTokenWebService,
        preferencesManager: PreferencesManager
    ): AuthenticationRepository {
        return AuthenticationRepositoryImpl(accessTokenWebService, preferencesManager)
    }
}