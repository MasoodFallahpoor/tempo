package ir.fallahpoor.tempo.data.repository.artists

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
import kotlinx.coroutines.*
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

    private var jobs = listOf<Job>()

    override fun getArtistAllInfo(artistId: String): LiveData<Resource<ArtistAllInfoEntity>> {

        val liveData = MutableLiveData<Resource<ArtistAllInfoEntity>>()
        lateinit var resource: Resource<ArtistAllInfoEntity>

        runBlocking {
            resource = try {
                getResource(
                    _getArtist(artistId),
                    _getArtistTopTracks(artistId, COUNTRY),
                    _getArtistAlbums(artistId, GROUPS, OFFSET, LIMIT),
                    _getArtistRelatedArtists(artistId)
                )
            } catch (t: Throwable) {
                Resource.Error(ExceptionHumanizer.getHumanizedErrorMessage(t))
            }
        }

        liveData.postValue(resource)

        return liveData

    }

    private suspend fun _getArtist(artistId: String): Response<ArtistEntity> =
        withContext(Dispatchers.IO) {
            artistsWebService.getArtist(artistId)
        }

    private suspend fun _getArtistTopTracks(
        artistId: String,
        country: String
    ): Response<TracksEnvelop> =
        withContext(Dispatchers.IO) {
            artistsWebService.getArtistTopTracks(artistId, country)
        }

    private suspend fun _getArtistAlbums(
        artistId: String,
        groups: String,
        offset: Int,
        limit: Int
    ): Response<AlbumsEnvelop> =
        withContext(Dispatchers.IO) {
            artistsWebService.getArtistAlbums(artistId, groups, offset, limit)
        }

    private suspend fun _getArtistRelatedArtists(artistId: String): Response<ArtistsEnvelop> =
        withContext(Dispatchers.IO) {
            artistsWebService.getArtistRelatedArtists(artistId)
        }

    private fun getResource(
        artistResponse: Response<ArtistEntity>,
        topTracksResponse: Response<TracksEnvelop>,
        albumsResponse: Response<AlbumsEnvelop>,
        relatedArtistsResponse: Response<ArtistsEnvelop>
    ): Resource<ArtistAllInfoEntity> {

        return if (artistResponse.isSuccessful &&
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

    }

    override fun getArtist(artistId: String): LiveData<Resource<ArtistEntity>> {

        val liveData = MutableLiveData<Resource<ArtistEntity>>()

        jobs = jobs + CoroutineScope(Dispatchers.IO).launch {

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

            liveData.postValue(resource)

        }

        return liveData

    }

    override fun getArtistAlbums(artistId: String): LiveData<Resource<List<AlbumEntity>>> {

        val liveData = MutableLiveData<Resource<List<AlbumEntity>>>()

        jobs = jobs + CoroutineScope(Dispatchers.IO).launch {

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

            liveData.postValue(resource)

        }

        return liveData

    }

    override fun getArtistTopTracks(artistId: String): LiveData<Resource<List<TrackEntity>>> {

        val liveData = MutableLiveData<Resource<List<TrackEntity>>>()

        jobs = jobs + CoroutineScope(Dispatchers.IO).launch {

            val resource: Resource<List<TrackEntity>> =
                try {
                    val response: Response<TracksEnvelop> =
                        artistsWebService.getArtistTopTracks(artistId, COUNTRY)
                    if (response.isSuccessful) {
                        Resource.Success(response.body()!!.tracks)
                    } else {
                        Resource.Error(response.message())
                    }
                } catch (t: Throwable) {
                    Resource.Error(ExceptionHumanizer.getHumanizedErrorMessage(t))
                }

            liveData.postValue(resource)

        }

        return liveData

    }

    override fun getArtistRelatedArtists(artistId: String): LiveData<Resource<List<ArtistEntity>>> {

        val liveData = MutableLiveData<Resource<List<ArtistEntity>>>()

        jobs = jobs + CoroutineScope(Dispatchers.IO).launch {

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

            liveData.postValue(resource)

        }

        return liveData

    }

    override fun dispose() {
        jobs.forEach { it.cancel() }
    }

}