package ir.fallahpoor.tempo.data.entity.artist

import com.google.gson.annotations.SerializedName
import ir.fallahpoor.tempo.data.entity.FollowersEntity
import ir.fallahpoor.tempo.data.entity.common.IconEntity

data class ArtistEntity(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("images")
    val images: List<IconEntity>,
    @SerializedName("genres")
    val genres: List<String>,
    @SerializedName("followers")
    val followers: FollowersEntity,
    @SerializedName("uri")
    val uri: String
)