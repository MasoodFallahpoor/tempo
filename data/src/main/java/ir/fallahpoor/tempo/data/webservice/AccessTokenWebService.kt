package ir.fallahpoor.tempo.data.webservice

import ir.fallahpoor.tempo.data.entity.AccessTokenEntity
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AccessTokenWebService {

    @POST("token")
    @FormUrlEncoded
    fun getAccessToken(@Field("grant_type") grantType: String): Call<AccessTokenEntity>

}