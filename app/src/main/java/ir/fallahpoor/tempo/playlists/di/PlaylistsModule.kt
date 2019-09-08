package ir.fallahpoor.tempo.playlists.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ir.fallahpoor.tempo.app.di.ViewModelKey
import ir.fallahpoor.tempo.playlists.viewmodel.PlaylistsViewModel

@Module
abstract class PlaylistsModule {
    @Binds
    @IntoMap
    @ViewModelKey(PlaylistsViewModel::class)
    abstract fun bindPlaylistsViewModel(playlistsViewModel: PlaylistsViewModel): ViewModel
}