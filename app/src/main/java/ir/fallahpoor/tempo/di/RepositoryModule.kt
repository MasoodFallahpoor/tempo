package ir.fallahpoor.tempo.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import ir.fallahpoor.tempo.data.repository.artists.ArtistsRepository
import ir.fallahpoor.tempo.data.repository.artists.ArtistsRepositoryImpl
import ir.fallahpoor.tempo.data.repository.authentication.AuthenticationRepository
import ir.fallahpoor.tempo.data.repository.authentication.AuthenticationRepositoryImpl
import ir.fallahpoor.tempo.data.repository.category.CategoriesRepository
import ir.fallahpoor.tempo.data.repository.category.CategoriesRepositoryImpl
import ir.fallahpoor.tempo.data.repository.search.SearchRepository
import ir.fallahpoor.tempo.data.repository.search.SearchRepositoryImpl

@Module
@InstallIn(ActivityComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun provideAuthenticationRepository(
        authenticationRepositoryImpl: AuthenticationRepositoryImpl
    ): AuthenticationRepository

    @Binds
    abstract fun provideCategoriesRepository(
        categoriesRepositoryImpl: CategoriesRepositoryImpl
    ): CategoriesRepository

    @Binds
    abstract fun provideSearchRepository(
        searchRepositoryImpl: SearchRepositoryImpl
    ): SearchRepository

    @Binds
    abstract fun provideArtistsRepository(
        artistsRepositoryImpl: ArtistsRepositoryImpl
    ): ArtistsRepository
}