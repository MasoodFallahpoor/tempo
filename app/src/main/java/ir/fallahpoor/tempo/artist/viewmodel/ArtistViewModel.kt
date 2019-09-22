package ir.fallahpoor.tempo.artist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import ir.fallahpoor.tempo.common.viewstate.DataErrorViewState
import ir.fallahpoor.tempo.common.viewstate.DataLoadedViewState
import ir.fallahpoor.tempo.common.viewstate.ViewState
import ir.fallahpoor.tempo.data.common.Resource
import ir.fallahpoor.tempo.data.entity.artist.ArtistAllInfoEntity
import ir.fallahpoor.tempo.data.repository.artists.ArtistsRepository
import javax.inject.Inject

// FIXME: Don't fetch data if it's already fetched.

class ArtistViewModel
@Inject constructor(
    private val artistsRepository: ArtistsRepository
) : ViewModel() {

    private val artistIdLiveData = MutableLiveData<String>()

    val artist: LiveData<ViewState> =
        Transformations.map(
            Transformations.switchMap(artistIdLiveData) {
                artistsRepository.getArtistAllInfo(it)
            },
            ::transformResourceToViewState
        )

    fun getArtist(artistId: String) {
        artistIdLiveData.value = artistId
    }

    private fun transformResourceToViewState(resource: Resource<ArtistAllInfoEntity>): ViewState =
        if (resource.status == Resource.Status.SUCCESS) {
            DataLoadedViewState(resource.data)
        } else {
            DataErrorViewState(resource.error!!.message)
        }

    override fun onCleared() {
        super.onCleared()
        artistsRepository.dispose()
    }

}