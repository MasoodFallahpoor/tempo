package ir.fallahpoor.tempo.app.di

import dagger.Component
import ir.fallahpoor.tempo.categories.di.CategoriesModule
import ir.fallahpoor.tempo.categories.view.CategoriesFragment
import ir.fallahpoor.tempo.playlists.di.PlaylistsModule
import ir.fallahpoor.tempo.playlists.view.PlaylistsFragment
import ir.fallahpoor.tempo.splash.view.SplashActivity
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, ViewModelFactoryModule::class, CategoriesModule::class, PlaylistsModule::class])
interface AppComponent {
    fun inject(splashActivity: SplashActivity)
    fun inject(categoriesFragment: CategoriesFragment)
    fun inject(playlistsFragment: PlaylistsFragment)
}