package ir.fallahpoor.tempo.data.entity.track

import com.google.gson.annotations.SerializedName
import ir.fallahpoor.tempo.data.entity.album.AlbumEntity
import ir.fallahpoor.tempo.data.entity.artist.ArtistEntity

data class TrackEntity(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("artists")
    val artists: List<ArtistEntity>,
    @SerializedName("album")
    val album: AlbumEntity
)