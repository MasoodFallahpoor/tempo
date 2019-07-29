package ir.fallahpoor.tempo.browse.di

import dagger.Component
import ir.fallahpoor.tempo.browse.view.BrowseCategoriesFragment

@Component(modules = [BrowseCategoriesModule::class])
interface BrowseCategoriesComponent {
    fun inject(browseCategoriesFragment: BrowseCategoriesFragment)
}