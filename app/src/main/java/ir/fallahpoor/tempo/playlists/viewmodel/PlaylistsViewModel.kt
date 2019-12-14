package ir.fallahpoor.tempo.playlists.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import ir.fallahpoor.tempo.common.extensions.map
import ir.fallahpoor.tempo.common.extensions.switchMap
import ir.fallahpoor.tempo.data.common.State
import ir.fallahpoor.tempo.data.entity.playlist.PlaylistEntity
import ir.fallahpoor.tempo.data.repository.ListResult
import ir.fallahpoor.tempo.data.repository.category.CategoriesRepository
import javax.inject.Inject

class PlaylistsViewModel
@Inject constructor(
    categoriesRepository: CategoriesRepository
) : ViewModel() {

    private val categoryIdLiveData = MutableLiveData<String>()
    private val playlistsResult: LiveData<ListResult<PlaylistEntity>> =
        categoryIdLiveData.map { categoryId: String ->
            categoriesRepository.getPlaylists(categoryId)
        }

    val playlists: LiveData<PagedList<PlaylistEntity>> =
        playlistsResult.switchMap { l: ListResult<PlaylistEntity> ->
            l.data
        }
    val state: LiveData<State> = playlistsResult.switchMap { l: ListResult<PlaylistEntity> ->
        l.state
    }

    fun getPlaylists(categoryId: String) {
        categoryIdLiveData.value = categoryId
    }

}