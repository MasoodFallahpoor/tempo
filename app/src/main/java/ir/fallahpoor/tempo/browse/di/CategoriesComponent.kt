package ir.fallahpoor.tempo.browse.di

import dagger.Component
import ir.fallahpoor.tempo.browse.view.CategoriesFragment
import javax.inject.Singleton

@Singleton
@Component(modules = [CategoriesModule::class])
interface CategoriesComponent {
    fun inject(categoriesFragment: CategoriesFragment)
}