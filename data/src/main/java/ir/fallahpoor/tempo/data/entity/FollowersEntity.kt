package ir.fallahpoor.tempo.data.entity

import com.google.gson.annotations.SerializedName

data class FollowersEntity(
    @SerializedName("href")
    val href: String?,
    @SerializedName("total")
    val total: Long
)