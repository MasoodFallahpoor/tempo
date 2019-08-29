package ir.fallahpoor.tempo.data.webservice

import androidx.lifecycle.LiveData
import ir.fallahpoor.tempo.data.common.Resource
import ir.fallahpoor.tempo.data.entity.category.CategoriesEnvelop
import ir.fallahpoor.tempo.data.entity.playlist.PlaylistsEnvelop
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CategoriesWebService {

    @GET("browse/categories")
    fun getCategories(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): LiveData<Resource<CategoriesEnvelop>>

    @GET("browse/categories/{categoryId}/playlists")
    fun getPlaylists(
        @Path("categoryId") categoryId: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): LiveData<Resource<PlaylistsEnvelop>>

}