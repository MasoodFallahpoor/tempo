package ir.fallahpoor.tempo.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
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
@InstallIn(ActivityComponent::class)
object WebServiceModule {
    @Provides
    fun provideAccessTokenWebService(webServiceFactory: WebServiceFactory): AccessTokenWebService {
        return webServiceFactory.createAuthenticationService(AccessTokenWebService::class.java)
    }

    @Provides
    fun provideCategoriesWebService(webServiceFactory: WebServiceFactory): CategoriesWebService {
        return webServiceFactory.createApiService(CategoriesWebService::class.java)
    }

    @Provides
    fun provideSearchWebService(webServiceFactory: WebServiceFactory): SearchWebService {
        return webServiceFactory.createApiService(SearchWebService::class.java)
    }

    @Provides
    fun provideArtistsWebService(webServiceFactory: WebServiceFactory): ArtistsWebService {
        return webServiceFactory.createApiService(ArtistsWebService::class.java)
    }
}