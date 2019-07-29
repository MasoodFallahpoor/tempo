package ir.fallahpoor.tempo.browse.di

import android.content.Context
import android.preference.PreferenceManager
import dagger.Module
import dagger.Provides
import ir.fallahpoor.tempo.data.repository.category.CategoriesRepository
import ir.fallahpoor.tempo.data.repository.category.CategoriesRepositoryImpl
import ir.fallahpoor.tempo.data.webservice.WebServiceFactory

@Module
class BrowseCategoriesModule(private val context: Context) {

    @Provides
    fun provideContext() = context

    @Provides
    internal fun provideCategoryRepository(webServiceFactory: WebServiceFactory): CategoriesRepository {
        return CategoriesRepositoryImpl(webServiceFactory)
    }

    @Provides
    internal fun provideSharedPreferences(context: Context) =
        PreferenceManager.getDefaultSharedPreferences(context)

}