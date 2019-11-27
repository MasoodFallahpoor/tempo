package ir.fallahpoor.tempo.artist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ir.fallahpoor.tempo.common.ViewState
import ir.fallahpoor.tempo.common.extensions.map
import ir.fallahpoor.tempo.common.extensions.switchMap
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

        artistIdLiveData.switchMap { artistId: String ->
            artistsRepository.getArtistAllInfo(artistId)
        }.map { resource: Resource<ArtistAllInfoEntity> ->
            when (resource) {
                is Resource.Success -> ViewState.DataLoaded(resource.data)
                is Resource.Error -> ViewState.Error(resource.errorMessage)
            }
        }

    fun getArtist(artistId: String) {
        artistIdLiveData.value = artistId
    }

}