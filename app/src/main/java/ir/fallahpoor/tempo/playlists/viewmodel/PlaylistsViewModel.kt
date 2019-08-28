package ir.fallahpoor.tempo.playlists.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import ir.fallahpoor.tempo.common.viewstate.*
import ir.fallahpoor.tempo.data.Resource
import ir.fallahpoor.tempo.data.entity.playlist.PlaylistsEntity
import ir.fallahpoor.tempo.data.repository.category.CategoriesRepository
import ir.fallahpoor.tempo.playlists.model.PlaylistsDataMapper

class PlaylistsViewModel(
    private val categoriesRepository: CategoriesRepository,
    private val playlistsDataMapper: PlaylistsDataMapper
) : ViewModel() {

    private companion object {
        const val LIMIT = 20
    }

    private var categoryId = ""
    private var totalCount = 0
    private var offset = 0
    private var playlistsLiveData: LiveData<Resource<PlaylistsEntity>>? = null
    private var moreCategoriesLiveData: LiveData<Resource<PlaylistsEntity>>? = null
    private val viewStateLiveData = MutableLiveData<ViewState>()
    private val playlistsObserver = Observer { resource: Resource<PlaylistsEntity> ->
        viewStateLiveData.value =
            if (resource.status == Resource.Status.SUCCESS) {
                totalCount = (resource.data?.total ?: 0)
                DataLoadedViewState(
                    playlistsDataMapper.map(
                        resource.data!!
                    )
                )
            } else {
                DataErrorViewState(resource.error!!.message)
            }
    }
    private val morePlaylistsObserver = Observer { resource: Resource<PlaylistsEntity> ->
        viewStateLiveData.value =
            if (resource.status == Resource.Status.SUCCESS) {
                offset += LIMIT
                MoreDataLoadedViewState(
                    playlistsDataMapper.map(
                        resource.data!!
                    )
                )
            } else {
                MoreDataErrorViewState(resource.error!!.message)
            }
    }

    fun getPlaylists(categoryId: String) {
        this.categoryId = categoryId
        if (viewStateLiveData.value == null || viewStateLiveData.value is DataErrorViewState) {
            offset = 0
            viewStateLiveData.value = LoadingViewState()
            playlistsLiveData = categoriesRepository.getPlaylists(categoryId,
                LIMIT, offset)
            playlistsLiveData?.observeForever(playlistsObserver)
        }
    }

    fun getViewStateLiveData() = viewStateLiveData

    fun getMorePlaylists() {
        if (offset < totalCount) {
            viewStateLiveData.value = LoadingViewState()
            moreCategoriesLiveData =
                categoriesRepository.getPlaylists(categoryId,
                    LIMIT, offset + LIMIT
                )
            moreCategoriesLiveData?.observeForever(morePlaylistsObserver)
        }
    }

    override fun onCleared() {
        super.onCleared()
        playlistsLiveData?.removeObserver(playlistsObserver)
        moreCategoriesLiveData?.removeObserver(morePlaylistsObserver)
    }

}