package ir.fallahpoor.tempo.search.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ir.fallahpoor.tempo.app.di.ViewModelKey
import ir.fallahpoor.tempo.search.viewmodel.SearchViewModel

@Module
abstract class SearchModule {
    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    abstract fun bindSearchViewModel(searchViewModel: SearchViewModel): ViewModel
}