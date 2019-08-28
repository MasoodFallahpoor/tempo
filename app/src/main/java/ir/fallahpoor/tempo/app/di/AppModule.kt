package ir.fallahpoor.tempo.app.di

import android.content.Context
import android.preference.PreferenceManager
import dagger.Module
import dagger.Provides
import ir.fallahpoor.tempo.data.PreferencesManager
import ir.fallahpoor.tempo.data.repository.authentication.AuthenticationRepository
import ir.fallahpoor.tempo.data.repository.authentication.AuthenticationRepositoryImpl
import ir.fallahpoor.tempo.data.repository.category.CategoriesRepository
import ir.fallahpoor.tempo.data.repository.category.CategoriesRepositoryImpl
import ir.fallahpoor.tempo.data.webservice.AccessTokenWebService
import ir.fallahpoor.tempo.data.webservice.CategoriesWebService
import ir.fallahpoor.tempo.data.webservice.WebServiceFactory

@Module
class AppModule(private val context: Context) {

    @Provides
    fun provideContext() = context

    @Provides
    internal fun provideCategoryRepository(categoriesWebService: CategoriesWebService): CategoriesRepository =
        CategoriesRepositoryImpl(categoriesWebService)

    @Provides
    internal fun provideAuthenticationRepository(
        accessTokenWebService: AccessTokenWebService,
        preferencesManager: PreferencesManager
    ): AuthenticationRepository =
        AuthenticationRepositoryImpl(accessTokenWebService, preferencesManager)

    @Provides
    internal fun provideCategoriesWebService(webServiceFactory: WebServiceFactory) =
        webServiceFactory.createApiService(CategoriesWebService::class.java)

    @Provides
    internal fun provideAccessTokenWebService(webServiceFactory: WebServiceFactory) =
        webServiceFactory.createAuthenticationService(AccessTokenWebService::class.java)

    @Provides
    internal fun provideSharedPreferences(context: Context) =
        PreferenceManager.getDefaultSharedPreferences(context)

}