package ir.fallahpoor.tempo.data.repository.artists

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import ir.fallahpoor.tempo.data.common.ExceptionHumanizer
import ir.fallahpoor.tempo.data.common.Resource
import ir.fallahpoor.tempo.data.entity.album.AlbumEntity
import ir.fallahpoor.tempo.data.entity.album.AlbumsEnvelop
import ir.fallahpoor.tempo.data.entity.artist.ArtistAllInfoEntity
import ir.fallahpoor.tempo.data.entity.artist.ArtistEntity
import ir.fallahpoor.tempo.data.entity.artist.ArtistsEnvelop
import ir.fallahpoor.tempo.data.entity.track.TrackEntity
import ir.fallahpoor.tempo.data.entity.track.TracksEnvelop
import ir.fallahpoor.tempo.data.webservice.ArtistsWebService
import retrofit2.Response
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

    override fun getArtistAllInfo(artistId: String): LiveData<Resource<ArtistAllInfoEntity>> =

        liveData {
            val resource: Resource<ArtistAllInfoEntity> =
                try {
                    getResource(
                        artistsWebService.getArtist(artistId),
                        artistsWebService.getArtistTopTracks(artistId, COUNTRY),
                        artistsWebService.getArtistAlbums(artistId, GROUPS, OFFSET, LIMIT),
                        artistsWebService.getArtistRelatedArtists(artistId)
                    )
                } catch (t: Throwable) {
                    Resource.Error(ExceptionHumanizer.getHumanizedErrorMessage(t))
                }
            emit(resource)
        }

    private fun getResource(
        artistResponse: Response<ArtistEntity>,
        topTracksResponse: Response<TracksEnvelop>,
        albumsResponse: Response<AlbumsEnvelop>,
        relatedArtistsResponse: Response<ArtistsEnvelop>
    ): Resource<ArtistAllInfoEntity> =

        if (artistResponse.isSuccessful &&
            topTracksResponse.isSuccessful &&
            albumsResponse.isSuccessful &&
            relatedArtistsResponse.isSuccessful
        ) {
            val artistAllInfoEntity = ArtistAllInfoEntity(
                artistResponse.body()!!,
                topTracksResponse.body()!!.tracks,
                albumsResponse.body()!!.items,
                relatedArtistsResponse.body()!!.artists
            )
            Resource.Success(artistAllInfoEntity)
        } else {
            Resource.Error(ExceptionHumanizer.SOMETHING_WENT_WRONG)
        }

    override fun getArtist(artistId: String): LiveData<Resource<ArtistEntity>> =
        liveData {
            val resource: Resource<ArtistEntity> =
                try {
                    val response: Response<ArtistEntity> = artistsWebService.getArtist(artistId)
                    if (response.isSuccessful) {
                        Resource.Success(response.body()!!)
                    } else {
                        Resource.Error(response.message())
                    }
                } catch (t: Throwable) {
                    Resource.Error(ExceptionHumanizer.getHumanizedErrorMessage(t))
                }
            emit(resource)
        }

    override fun getArtistAlbums(artistId: String): LiveData<Resource<List<AlbumEntity>>> =
        liveData {
            val resource: Resource<List<AlbumEntity>> =
                try {
                    val response: Response<AlbumsEnvelop> =
                        artistsWebService.getArtistAlbums(artistId, GROUPS, OFFSET, LIMIT)
                    if (response.isSuccessful) {
                        Resource.Success(response.body()?.items!!)
                    } else {
                        Resource.Error(response.message())
                    }
                } catch (t: Throwable) {
                    Resource.Error(ExceptionHumanizer.getHumanizedErrorMessage(t))
                }
            emit(resource)
        }

    override fun getArtistTopTracks(artistId: String): LiveData<Resource<List<TrackEntity>>> =
        liveData {
            val resource: Resource<List<TrackEntity>> =
                try {
                    val response: Response<TracksEnvelop> =
                        artistsWebService.getArtistTopTracks(artistId, COUNTRY)
                    if (response.isSuccessful) {
                        Resource.Success(response.body()?.tracks!!)
                    } else {
                        Resource.Error(response.message())
                    }
                } catch (t: Throwable) {
                    Resource.Error(ExceptionHumanizer.getHumanizedErrorMessage(t))
                }
            emit(resource)
        }

    override fun getArtistRelatedArtists(artistId: String): LiveData<Resource<List<ArtistEntity>>> =
        liveData {
            val resource: Resource<List<ArtistEntity>> =
                try {
                    val response: Response<ArtistsEnvelop> =
                        artistsWebService.getArtistRelatedArtists(artistId)
                    if (response.isSuccessful) {
                        Resource.Success(response.body()?.artists!!)
                    } else {
                        Resource.Error(response.message())
                    }
                } catch (t: Throwable) {
                    Resource.Error(ExceptionHumanizer.getHumanizedErrorMessage(t))
                }
            emit(resource)
        }

    override fun dispose() {
    }

}