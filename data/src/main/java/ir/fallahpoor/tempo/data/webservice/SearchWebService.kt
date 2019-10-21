package ir.fallahpoor.tempo.data.webservice

import ir.fallahpoor.tempo.data.entity.SearchEntity
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchWebService {
    @GET("search")
    suspend fun search(
        @Query("q") query: String,
        @Query("type") type: String,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): Response<SearchEntity>
}