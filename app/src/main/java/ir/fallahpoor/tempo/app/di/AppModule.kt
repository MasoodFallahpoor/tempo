package ir.fallahpoor.tempo.app.di

import android.app.Application
import android.content.SharedPreferences
import android.preference.PreferenceManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
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
@InstallIn(ApplicationComponent::class)
object AppModule {
    @Provides
    fun provideSharedPreferences(application: Application): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(application)
    }
}