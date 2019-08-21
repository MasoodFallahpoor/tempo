package ir.fallahpoor.tempo.browse.di

import dagger.Component
import ir.fallahpoor.tempo.browse.view.BrowseCategoriesFragment
import javax.inject.Singleton

@Singleton
@Component(modules = [BrowseCategoriesModule::class])
interface BrowseCategoriesComponent {
    fun inject(browseCategoriesFragment: BrowseCategoriesFragment)
}