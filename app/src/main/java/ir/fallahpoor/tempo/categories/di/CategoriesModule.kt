package ir.fallahpoor.tempo.categories.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ir.fallahpoor.tempo.app.di.ViewModelKey
import ir.fallahpoor.tempo.categories.viewmodel.CategoriesViewModel

@Module
abstract class CategoriesModule {
    @Binds
    @IntoMap
    @ViewModelKey(CategoriesViewModel::class)
    abstract fun bindCategoriesViewModel(categoriesViewModel: CategoriesViewModel): ViewModel
}