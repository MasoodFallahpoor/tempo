package ir.fallahpoor.tempo.data.webservice

import io.reactivex.Single
import ir.fallahpoor.tempo.data.entity.category.CategoriesEnvelop
import ir.fallahpoor.tempo.data.entity.playlist.PlaylistsEnvelop
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CategoriesWebService {

    @GET("browse/categories")
    fun getCategories(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): Single<CategoriesEnvelop>

    @GET("browse/categories/{categoryId}/playlists")
    fun getPlaylists(
        @Path("categoryId") categoryId: String,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): Single<PlaylistsEnvelop>

}