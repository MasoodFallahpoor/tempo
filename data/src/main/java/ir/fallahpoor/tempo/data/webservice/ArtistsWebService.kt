package ir.fallahpoor.tempo.data.webservice

import io.reactivex.Single
import ir.fallahpoor.tempo.data.entity.album.AlbumsEnvelop
import ir.fallahpoor.tempo.data.entity.artist.ArtistEntity
import ir.fallahpoor.tempo.data.entity.artist.ArtistsEnvelop
import ir.fallahpoor.tempo.data.entity.track.TracksEnvelop
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ArtistsWebService {

    @GET("artists/{artistId}")
    fun getArtist(@Path("artistId") artistId: String): Single<ArtistEntity>

    @GET("artists/{artistId}/albums")
    fun getArtistAlbums(
        @Path("artistId") artistId: String,
        @Query("include_groups") groups: String,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): Single<AlbumsEnvelop>

    @GET("artists/{artistId}/top-tracks")
    fun getArtistTopTracks(
        @Path("artistId") artistId: String,
        @Query("country") country: String
    ): Single<TracksEnvelop>

    @GET("artists/{artistId}/related-artists")
    fun getArtistRelatedArtists(@Path("artistId") artistId: String): Single<ArtistsEnvelop>

}