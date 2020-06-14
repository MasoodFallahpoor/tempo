package ir.fallahpoor.tempo.main.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import ir.fallahpoor.tempo.data.repository.artists.ArtistsRepository
import ir.fallahpoor.tempo.data.repository.artists.ArtistsRepositoryImpl
import ir.fallahpoor.tempo.data.repository.category.CategoriesRepository
import ir.fallahpoor.tempo.data.repository.category.CategoriesRepositoryImpl
import ir.fallahpoor.tempo.data.repository.search.SearchRepository
import ir.fallahpoor.tempo.data.repository.search.SearchRepositoryImpl
import ir.fallahpoor.tempo.data.webservice.ArtistsWebService
import ir.fallahpoor.tempo.data.webservice.CategoriesWebService
import ir.fallahpoor.tempo.data.webservice.SearchWebService
import ir.fallahpoor.tempo.data.webservice.WebServiceFactory

@Module
@InstallIn(ActivityComponent::class)
object ActivityModule {
    @Provides
    fun provideCategoriesWebService(webServiceFactory: WebServiceFactory): CategoriesWebService {
        return webServiceFactory.createApiService(CategoriesWebService::class.java)
    }

    @Provides
    fun provideCategoriesRepository(categoriesWebService: CategoriesWebService): CategoriesRepository {
        return CategoriesRepositoryImpl(categoriesWebService)
    }

    @Provides
    fun provideSearchWebService(webServiceFactory: WebServiceFactory): SearchWebService {
        return webServiceFactory.createApiService(SearchWebService::class.java)
    }

    @Provides
    fun provideSearchRepository(searchWebService: SearchWebService): SearchRepository {
        return SearchRepositoryImpl(searchWebService)
    }

    @Provides
    fun provideArtistsWebService(webServiceFactory: WebServiceFactory): ArtistsWebService {
        return webServiceFactory.createApiService(ArtistsWebService::class.java)
    }

    @Provides
    fun provideArtistsRepository(artistsWebService: ArtistsWebService): ArtistsRepository {
        return ArtistsRepositoryImpl(artistsWebService)
    }
}