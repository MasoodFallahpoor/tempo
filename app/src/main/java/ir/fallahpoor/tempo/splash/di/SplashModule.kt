package ir.fallahpoor.tempo.splash.di

import android.content.Context
import android.preference.PreferenceManager
import dagger.Module
import dagger.Provides
import ir.fallahpoor.tempo.data.PreferencesManager
import ir.fallahpoor.tempo.data.repository.authentication.AuthenticationRepository
import ir.fallahpoor.tempo.data.repository.authentication.AuthenticationRepositoryImpl
import ir.fallahpoor.tempo.data.webservice.WebServiceFactory

@Module
class SplashModule(private val context: Context) {

    @Provides
    fun provideContext() = context

    @Provides
    internal fun provideAuthenticationRepository(
        webServiceFactory: WebServiceFactory,
        preferencesManager: PreferencesManager
    ): AuthenticationRepository {
        return AuthenticationRepositoryImpl(webServiceFactory, preferencesManager)
    }

    @Provides
    internal fun provideSharedPreferences(context: Context) =
        PreferenceManager.getDefaultSharedPreferences(context)

}