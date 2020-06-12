package ir.fallahpoor.tempo.data.repository.artists

import io.reactivex.Single
import ir.fallahpoor.tempo.data.entity.album.AlbumEntity
import ir.fallahpoor.tempo.data.entity.artist.ArtistAllInfoEntity
import ir.fallahpoor.tempo.data.entity.artist.ArtistEntity
import ir.fallahpoor.tempo.data.entity.track.TrackEntity

interface ArtistsRepository {
    fun getArtist(artistId: String): Single<ArtistEntity>
    fun getArtistAlbums(artistId: String): Single<List<AlbumEntity>>
    fun getArtistTopTracks(artistId: String): Single<List<TrackEntity>>
    fun getArtistRelatedArtists(artistId: String): Single<List<ArtistEntity>>
    fun getArtistAllInfo(artistId: String): Single<ArtistAllInfoEntity>
}