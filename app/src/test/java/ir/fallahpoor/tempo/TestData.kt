package ir.fallahpoor.tempo

import ir.fallahpoor.tempo.data.entity.SearchEntity
import ir.fallahpoor.tempo.data.entity.album.AlbumEntity
import ir.fallahpoor.tempo.data.entity.artist.ArtistEntity
import ir.fallahpoor.tempo.data.entity.common.ListEntity
import ir.fallahpoor.tempo.data.entity.playlist.PlaylistEntity
import ir.fallahpoor.tempo.data.entity.track.TrackEntity

object TestData {

    fun getSearchEntity() =
        SearchEntity(getAlbums(), getArtists(), getTracks(), getPlayLists())

    private fun getArtists() =
        ListEntity<ArtistEntity>("href", emptyList(), 50, "next url", 0, "prev url", 20)

    private fun getAlbums() =
        ListEntity<AlbumEntity>("href", emptyList(), 50, "next url", 0, "prev url", 20)

    private fun getTracks() =
        ListEntity<TrackEntity>("href", emptyList(), 50, "next url", 0, "prev url", 20)

    private fun getPlayLists() =
        ListEntity<PlaylistEntity>("href", emptyList(), 50, "next url", 0, "prev url", 20)

}