package ir.fallahpoor.tempo.data.webservice

import ir.fallahpoor.tempo.data.entity.album.AlbumsEnvelop
import ir.fallahpoor.tempo.data.entity.artist.ArtistEntity
import ir.fallahpoor.tempo.data.entity.artist.ArtistsEnvelop
import ir.fallahpoor.tempo.data.entity.track.TracksEnvelop
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ArtistsWebService {

    @GET("artists/{artistId}")
    suspend fun getArtist(@Path("artistId") artistId: String): Response<ArtistEntity>

    @GET("artists/{artistId}/albums")
    suspend fun getArtistAlbums(
        @Path("artistId") artistId: String,
        @Query("include_groups") groups: String,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): Response<AlbumsEnvelop>

    @GET("artists/{artistId}/top-tracks")
    suspend fun getArtistTopTracks(
        @Path("artistId") artistId: String,
        @Query("country") country: String
    ): Response<TracksEnvelop>

    @GET("artists/{artistId}/related-artists")
    suspend fun getArtistRelatedArtists(@Path("artistId") artistId: String): Response<ArtistsEnvelop>

}