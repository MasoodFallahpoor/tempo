package ir.fallahpoor.tempo.data.entity.common

import com.google.gson.annotations.SerializedName

data class ListEntity<T>(
    @SerializedName("href")
    val href: String,
    @SerializedName("items")
    val items: List<T>,
    @SerializedName("limit")
    val limit: Int,
    @SerializedName("next")
    val next: String?,
    @SerializedName("offset")
    val offset: Int,
    @SerializedName("previous")
    val previous: String?,
    @SerializedName("total")
    val total: Int
)