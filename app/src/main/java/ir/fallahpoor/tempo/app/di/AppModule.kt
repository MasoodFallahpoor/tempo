package ir.fallahpoor.tempo.app.di

import android.content.Context
import android.preference.PreferenceManager
import dagger.Module
import dagger.Provides
import ir.fallahpoor.tempo.data.common.PreferencesManager
import ir.fallahpoor.tempo.data.repository.artists.ArtistsRepository
import ir.fallahpoor.tempo.data.repository.artists.ArtistsRepositoryImpl
import ir.fallahpoor.tempo.data.repository.authentication.AuthenticationRepository
import ir.fallahpoor.tempo.data.repository.authentication.AuthenticationRepositoryImpl
import ir.fallahpoor.tempo.data.repository.category.CategoriesRepository
import ir.fallahpoor.tempo.data.repository.category.CategoriesRepositoryImpl
import ir.fallahpoor.tempo.data.repository.search.SearchRepository
import ir.fallahpoor.tempo.data.repository.search.SearchRepositoryImpl
import ir.fallahpoor.tempo.data.webservice.*

@Module
class AppModule(private val context: Context) {

    @Provides
    fun provideContext() = context

    @Provides
    internal fun provideCategoryRepository(
        categoriesWebService: CategoriesWebService
    ): CategoriesRepository =
        CategoriesRepositoryImpl(categoriesWebService)

    @Provides
    internal fun provideAuthenticationRepository(
        accessTokenWebService: AccessTokenWebService,
        preferencesManager: PreferencesManager
    ): AuthenticationRepository =
        AuthenticationRepositoryImpl(accessTokenWebService, preferencesManager)

    @Provides
    internal fun provideSearchRepository(searchWebService: SearchWebService): SearchRepository =
        SearchRepositoryImpl(searchWebService)

    @Provides
    internal fun provideArtistsRepository(artistsWebService: ArtistsWebService): ArtistsRepository =
        ArtistsRepositoryImpl(artistsWebService)

    @Provides
    internal fun provideCategoriesWebService(webServiceFactory: WebServiceFactory) =
        webServiceFactory.createApiService(CategoriesWebService::class.java)

    @Provides
    internal fun provideAccessTokenWebService(webServiceFactory: WebServiceFactory) =
        webServiceFactory.createAuthenticationService(AccessTokenWebService::class.java)

    @Provides
    internal fun provideSearchWebService(webServiceFactory: WebServiceFactory) =
        webServiceFactory.createApiService(SearchWebService::class.java)

    @Provides
    internal fun provideArtistsWebService(webServiceFactory: WebServiceFactory) =
        webServiceFactory.createApiService(ArtistsWebService::class.java)

    @Provides
    internal fun provideSharedPreferences(context: Context) =
        PreferenceManager.getDefaultSharedPreferences(context)

}