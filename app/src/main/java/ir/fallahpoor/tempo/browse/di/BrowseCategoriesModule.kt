package ir.fallahpoor.tempo.browse.di

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import dagger.Module
import dagger.Provides
import ir.fallahpoor.tempo.data.repository.category.CategoriesRepository
import ir.fallahpoor.tempo.data.repository.category.CategoriesRepositoryImpl
import ir.fallahpoor.tempo.data.webservice.CategoriesWebService
import ir.fallahpoor.tempo.data.webservice.WebServiceFactory
import javax.inject.Singleton

@Module
class BrowseCategoriesModule(private val context: Context) {

    @Provides
    fun provideContext() = context

    @Provides
    internal fun provideCategoryRepository(categoriesWebService: CategoriesWebService): CategoriesRepository =
        CategoriesRepositoryImpl(categoriesWebService)

    @Provides
    internal fun provideSharedPreferences(context: Context): SharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(context)

    @Provides
    @Singleton
    internal fun provideCategoriesWebService(webServiceFactory: WebServiceFactory): CategoriesWebService =
        webServiceFactory.createApiService(CategoriesWebService::class.java)

}