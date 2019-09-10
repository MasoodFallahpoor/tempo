package ir.fallahpoor.tempo.data.entity.album

import com.google.gson.annotations.SerializedName
import ir.fallahpoor.tempo.data.entity.artist.ArtistEntity
import ir.fallahpoor.tempo.data.entity.common.IconEntity

data class AlbumEntity(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("artists")
    val artists: List<ArtistEntity>,
    @SerializedName("album_type")
    val type: String,
    @SerializedName("images")
    val images: List<IconEntity>,
    @SerializedName("total_tracks")
    val totalTracks: Int
)