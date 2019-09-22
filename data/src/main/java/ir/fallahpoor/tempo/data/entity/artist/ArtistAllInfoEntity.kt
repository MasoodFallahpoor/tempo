package ir.fallahpoor.tempo.data.entity.artist

import ir.fallahpoor.tempo.data.entity.album.AlbumEntity
import ir.fallahpoor.tempo.data.entity.track.TrackEntity

data class ArtistAllInfoEntity(
    val artist: ArtistEntity,
    val topTracks: List<TrackEntity>,
    val albums: List<AlbumEntity>,
    val relatedArtists: List<ArtistEntity>
)