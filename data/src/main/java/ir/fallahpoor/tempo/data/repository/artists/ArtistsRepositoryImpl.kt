package ir.fallahpoor.tempo.data.repository.artists

import io.reactivex.Single
import io.reactivex.functions.Function4
import ir.fallahpoor.tempo.data.entity.album.AlbumEntity
import ir.fallahpoor.tempo.data.entity.album.AlbumsEnvelop
import ir.fallahpoor.tempo.data.entity.artist.ArtistAllInfoEntity
import ir.fallahpoor.tempo.data.entity.artist.ArtistEntity
import ir.fallahpoor.tempo.data.entity.artist.ArtistsEnvelop
import ir.fallahpoor.tempo.data.entity.track.TrackEntity
import ir.fallahpoor.tempo.data.entity.track.TracksEnvelop
import ir.fallahpoor.tempo.data.webservice.ArtistsWebService
import javax.inject.Inject

class ArtistsRepositoryImpl
@Inject constructor(
    private val artistsWebService: ArtistsWebService
) : ArtistsRepository {

    companion object {
        private const val OFFSET = 0
        private const val LIMIT = 50
        private const val GROUPS = "album"
        private const val COUNTRY = "us"
    }

    override fun getArtistAllInfo(artistId: String): Single<ArtistAllInfoEntity> =

        Single.zip(artistsWebService.getArtist(artistId),
            artistsWebService.getArtistTopTracks(artistId, COUNTRY),
            artistsWebService.getArtistAlbums(artistId, GROUPS, OFFSET, LIMIT),
            artistsWebService.getArtistRelatedArtists(artistId),
            Function4 { artistEntity: ArtistEntity, tracksEnvelop: TracksEnvelop, albumsEnvelop: AlbumsEnvelop, artistsEnvelop: ArtistsEnvelop ->
                ArtistAllInfoEntity(
                    artistEntity,
                    tracksEnvelop.tracks,
                    albumsEnvelop.items,
                    artistsEnvelop.artists
                )
            })

    override fun getArtist(artistId: String): Single<ArtistEntity> =
        artistsWebService.getArtist(artistId)

    override fun getArtistAlbums(artistId: String): Single<List<AlbumEntity>> =
        artistsWebService.getArtistAlbums(artistId, GROUPS, OFFSET, LIMIT)
            .map { albumsEnvelop: AlbumsEnvelop ->
                albumsEnvelop.items
            }

    override fun getArtistTopTracks(artistId: String): Single<List<TrackEntity>> =
        artistsWebService.getArtistTopTracks(artistId, COUNTRY)
            .map { tracksEnvelop: TracksEnvelop ->
                tracksEnvelop.tracks
            }

    override fun getArtistRelatedArtists(artistId: String): Single<List<ArtistEntity>> =
        artistsWebService.getArtistRelatedArtists(artistId)
            .map { artistsEnvelop: ArtistsEnvelop ->
                artistsEnvelop.artists
            }

}