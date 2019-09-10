package ir.fallahpoor.tempo.data.entity

import com.google.gson.annotations.SerializedName
import ir.fallahpoor.tempo.data.entity.album.AlbumEntity
import ir.fallahpoor.tempo.data.entity.artist.ArtistEntity
import ir.fallahpoor.tempo.data.entity.common.ListEntity
import ir.fallahpoor.tempo.data.entity.playlist.PlaylistEntity
import ir.fallahpoor.tempo.data.entity.track.TrackEntity

data class SearchEntity(
    @SerializedName("albums")
    val albums: ListEntity<AlbumEntity>,
    @SerializedName("artists")
    val artists: ListEntity<ArtistEntity>,
    @SerializedName("tracks")
    val tracks: ListEntity<TrackEntity>,
    @SerializedName("playlists")
    val playlists: ListEntity<PlaylistEntity>
)