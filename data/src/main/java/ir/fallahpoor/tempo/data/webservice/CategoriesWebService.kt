package ir.fallahpoor.tempo.data.webservice

import ir.fallahpoor.tempo.data.entity.CategoriesEnvelop
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CategoriesWebService {

    @GET("browse/categories")
    fun getCategories(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Call<CategoriesEnvelop>

}