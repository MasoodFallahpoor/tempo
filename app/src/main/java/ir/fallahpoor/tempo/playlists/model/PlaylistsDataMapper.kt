package ir.fallahpoor.tempo.playlists.model

import ir.fallahpoor.tempo.data.entity.IconEntity
import ir.fallahpoor.tempo.data.entity.playlist.PlaylistEntity
import ir.fallahpoor.tempo.data.entity.playlist.PlaylistsEntity
import javax.inject.Inject

class PlaylistsDataMapper @Inject constructor() {

    fun map(playlistsEntity: PlaylistsEntity): List<Playlist> =
        playlistsEntity.items.map { mapPlaylist(it) }

    private fun mapPlaylist(playlistEntity: PlaylistEntity) =
        Playlist(
            playlistEntity.id,
            playlistEntity.name,
            mapImages(playlistEntity.images)
        )

    private fun mapImages(imageList: List<IconEntity>): List<Icon> =
        imageList.map { Icon(it.width, it.height, it.url) }

}