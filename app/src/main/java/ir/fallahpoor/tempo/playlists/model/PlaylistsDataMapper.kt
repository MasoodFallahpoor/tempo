package ir.fallahpoor.tempo.playlists.model

import ir.fallahpoor.tempo.data.entity.common.ListEntity
import ir.fallahpoor.tempo.data.entity.common.IconEntity
import ir.fallahpoor.tempo.data.entity.playlist.PlaylistEntity
import javax.inject.Inject

class PlaylistsDataMapper @Inject constructor() {

    fun map(playlistsEntity: ListEntity<PlaylistEntity>): List<Playlist> =
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