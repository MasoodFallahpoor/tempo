package ir.fallahpoor.tempo.playlists.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ir.fallahpoor.tempo.data.repository.category.CategoriesRepository
import javax.inject.Inject

class PlaylistsViewModelFactory @Inject
constructor(
    private val categoriesRepository: CategoriesRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(viewModelClass: Class<T>): T {

        if (viewModelClass.isAssignableFrom(PlaylistsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PlaylistsViewModel(categoriesRepository) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class")
        }

    }

}