package ir.fallahpoor.tempo.data.entity.album

import com.google.gson.annotations.SerializedName
import ir.fallahpoor.tempo.data.entity.artist.ArtistEntity
import ir.fallahpoor.tempo.data.entity.common.IconEntity
import ir.fallahpoor.tempo.data.entity.track.TrackEntity

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
    val totalTracks: Int,
    @SerializedName("tracks")
    val tracks: List<TrackEntity>,
    @SerializedName("label")
    val label: String,
    @SerializedName("release_date")
    val releaseDate: String
)