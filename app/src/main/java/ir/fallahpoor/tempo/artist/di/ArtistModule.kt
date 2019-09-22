package ir.fallahpoor.tempo.artist.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ir.fallahpoor.tempo.app.di.ViewModelKey
import ir.fallahpoor.tempo.artist.viewmodel.ArtistViewModel

@Module
abstract class ArtistModule {
    @Binds
    @IntoMap
    @ViewModelKey(ArtistViewModel::class)
    abstract fun bindSearchViewModel(artistViewModel: ArtistViewModel): ViewModel
}