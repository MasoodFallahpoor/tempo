package ir.fallahpoor.tempo.playlists.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import ir.fallahpoor.tempo.data.repository.ListResult
import ir.fallahpoor.tempo.data.common.State
import ir.fallahpoor.tempo.data.entity.playlist.PlaylistEntity
import ir.fallahpoor.tempo.data.repository.category.CategoriesRepository

class PlaylistsViewModel(categoriesRepository: CategoriesRepository) : ViewModel() {

    private val categoryIdLiveData = MutableLiveData<String>()
    private val playlistsResult: LiveData<ListResult<PlaylistEntity>> =
        Transformations.map(categoryIdLiveData) {
            categoriesRepository.getPlaylists(it)
        }

    val playlists: LiveData<PagedList<PlaylistEntity>> =
        Transformations.switchMap(playlistsResult) {
            it.data
        }
    val state: LiveData<State> = Transformations.switchMap(playlistsResult) {
        it.state
    }

    fun getPlaylists(categoryId: String) {
        categoryIdLiveData.value = categoryId
    }

}