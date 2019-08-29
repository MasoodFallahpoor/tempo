package ir.fallahpoor.tempo.data.entity.playlist

import com.google.gson.annotations.SerializedName
import ir.fallahpoor.tempo.data.entity.common.IconEntity

data class PlaylistEntity(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("images")
    val images: List<IconEntity>
)