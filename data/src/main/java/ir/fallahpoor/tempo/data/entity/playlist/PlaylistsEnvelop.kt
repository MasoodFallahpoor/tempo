package ir.fallahpoor.tempo.data.entity.playlist

import com.google.gson.annotations.SerializedName
import ir.fallahpoor.tempo.data.entity.common.ListEntity

data class PlaylistsEnvelop(
    @SerializedName("playlists")
    val playlistsEntity: ListEntity<PlaylistEntity>
)