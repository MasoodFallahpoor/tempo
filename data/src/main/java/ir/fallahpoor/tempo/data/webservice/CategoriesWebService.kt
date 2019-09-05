package ir.fallahpoor.tempo.data.webservice

import ir.fallahpoor.tempo.data.entity.category.CategoriesEnvelop
import ir.fallahpoor.tempo.data.entity.playlist.PlaylistsEnvelop
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CategoriesWebService {

    @GET("browse/categories")
    fun getCategories(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): Call<CategoriesEnvelop>

    @GET("browse/categories/{categoryId}/playlists")
    fun getPlaylists(
        @Path("categoryId") categoryId: String,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): Call<PlaylistsEnvelop>

}