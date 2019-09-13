package ir.fallahpoor.tempo.splash.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ir.fallahpoor.tempo.app.di.ViewModelKey
import ir.fallahpoor.tempo.splash.viewmodel.SplashViewModel

@Module
abstract class SplashModule {
    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel::class)
    abstract fun bindSplashViewModel(splashViewModel: SplashViewModel): ViewModel
}