package ir.fallahpoor.tempo.data.entity.album

import com.google.gson.annotations.SerializedName

data class AlbumsEnvelop(
    @SerializedName("href")
    val href: String,
    @SerializedName("items")
    val items: List<AlbumEntity>,
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