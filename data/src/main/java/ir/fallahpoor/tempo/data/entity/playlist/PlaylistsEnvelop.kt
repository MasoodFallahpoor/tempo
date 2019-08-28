package ir.fallahpoor.tempo.data.entity.playlist

import com.google.gson.annotations.SerializedName

data class PlaylistsEnvelop(
    @SerializedName("playlists")
    val playlistsEntity: PlaylistsEntity
)