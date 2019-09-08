package ir.fallahpoor.tempo.app.di

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import ir.fallahpoor.tempo.common.ViewModelFactory

@Module
abstract class ViewModelFactoryModule {
    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory
}