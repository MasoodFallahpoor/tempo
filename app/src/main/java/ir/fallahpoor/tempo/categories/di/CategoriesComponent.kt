package ir.fallahpoor.tempo.categories.di

import dagger.Component
import ir.fallahpoor.tempo.categories.view.CategoriesFragment
import javax.inject.Singleton

@Singleton
@Component(modules = [CategoriesModule::class])
interface CategoriesComponent {
    fun inject(categoriesFragment: CategoriesFragment)
}