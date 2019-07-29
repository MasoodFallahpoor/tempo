package ir.fallahpoor.tempo.splash.di

import dagger.Component
import ir.fallahpoor.tempo.splash.view.SplashActivity

@Component(modules = [SplashModule::class])
interface SplashComponent {
    fun inject(splashActivity: SplashActivity)
}