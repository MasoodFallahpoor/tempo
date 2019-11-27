package ir.fallahpoor.tempo.data.repository.artists

import androidx.lifecycle.LiveData
import ir.fallahpoor.tempo.data.common.Resource
import ir.fallahpoor.tempo.data.entity.album.AlbumEntity
import ir.fallahpoor.tempo.data.entity.artist.ArtistAllInfoEntity
import ir.fallahpoor.tempo.data.entity.artist.ArtistEntity
import ir.fallahpoor.tempo.data.entity.track.TrackEntity

interface ArtistsRepository {
    fun getArtist(artistId: String): LiveData<Resource<ArtistEntity>>
    fun getArtistAlbums(artistId: String): LiveData<Resource<List<AlbumEntity>>>
    fun getArtistTopTracks(artistId: String): LiveData<Resource<List<TrackEntity>>>
    fun getArtistRelatedArtists(artistId: String): LiveData<Resource<List<ArtistEntity>>>
    fun getArtistAllInfo(artistId: String): LiveData<Resource<ArtistAllInfoEntity>>
}