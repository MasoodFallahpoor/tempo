package ir.fallahpoor.tempo.playlists.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ir.fallahpoor.tempo.data.repository.category.CategoriesRepository
import ir.fallahpoor.tempo.playlists.model.PlaylistsDataMapper
import javax.inject.Inject

class PlaylistsViewModelFactory @Inject
constructor(
    private val categoriesRepository: CategoriesRepository,
    private val playlistsDataMapper: PlaylistsDataMapper
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(viewModelClass: Class<T>): T {

        if (viewModelClass.isAssignableFrom(PlaylistsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PlaylistsViewModel(categoriesRepository, playlistsDataMapper) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class")
        }

    }

}