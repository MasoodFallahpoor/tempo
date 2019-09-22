package ir.fallahpoor.tempo.app.di

import dagger.Component
import ir.fallahpoor.tempo.artist.di.ArtistModule
import ir.fallahpoor.tempo.artist.view.ArtistFragment
import ir.fallahpoor.tempo.categories.di.CategoriesModule
import ir.fallahpoor.tempo.categories.view.CategoriesFragment
import ir.fallahpoor.tempo.playlists.di.PlaylistsModule
import ir.fallahpoor.tempo.playlists.view.PlaylistsFragment
import ir.fallahpoor.tempo.search.di.SearchModule
import ir.fallahpoor.tempo.search.view.SearchFragment
import ir.fallahpoor.tempo.splash.di.SplashModule
import ir.fallahpoor.tempo.splash.view.SplashActivity
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, ViewModelFactoryModule::class, SplashModule::class, CategoriesModule::class, PlaylistsModule::class, SearchModule::class, ArtistModule::class])
interface AppComponent {
    fun inject(splashActivity: SplashActivity)
    fun inject(categoriesFragment: CategoriesFragment)
    fun inject(playlistsFragment: PlaylistsFragment)
    fun inject(searchFragment: SearchFragment)
    fun inject(artistFragment: ArtistFragment)
}