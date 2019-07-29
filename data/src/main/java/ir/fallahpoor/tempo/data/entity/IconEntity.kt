package ir.fallahpoor.tempo.data.entity

import com.google.gson.annotations.SerializedName

data class IconEntity(
    @SerializedName("width")
    val width: Int,
    @SerializedName("height")
    val height: Int,
    @SerializedName("url")
    val url: String
)