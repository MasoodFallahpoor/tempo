package ir.fallahpoor.tempo.artist.model

import ir.fallahpoor.tempo.data.entity.album.AlbumEntity
import ir.fallahpoor.tempo.data.entity.artist.ArtistEntity
import ir.fallahpoor.tempo.data.entity.common.IconEntity
import ir.fallahpoor.tempo.data.entity.track.TrackEntity

data class Artist(
    val id: String,
    val name: String,
    val images: List<IconEntity>,
    val genres: List<String>,
    val followers: Long,
    val albums: List<AlbumEntity>,
    val topTracks: List<TrackEntity>,
    val relatedArtists: List<ArtistEntity>
)