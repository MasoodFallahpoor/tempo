package ir.fallahpoor.tempo.data.webservice

import ir.fallahpoor.tempo.data.entity.SearchEntity
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchWebService {
    @GET("search")
    fun search(
        @Query("q") query: String,
        @Query("type") type: String,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): Call<SearchEntity>
}