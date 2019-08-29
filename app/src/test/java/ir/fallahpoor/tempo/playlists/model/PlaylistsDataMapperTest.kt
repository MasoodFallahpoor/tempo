package ir.fallahpoor.tempo.playlists.model

import com.google.common.truth.Truth
import ir.fallahpoor.tempo.data.entity.common.IconEntity
import ir.fallahpoor.tempo.data.entity.playlist.PlaylistEntity
import ir.fallahpoor.tempo.data.entity.playlist.PlaylistsEntity
import org.junit.Test

class PlaylistsDataMapperTest {

    private val playlistsDataMapper = PlaylistsDataMapper()

    @Test
    fun test_map() {

        // Given
        val expectedPlaylists: List<Playlist> = getTestPlaylists()

        // When
        val actualPlaylists: List<Playlist> = playlistsDataMapper.map(getTestPlaylistsEntity())

        // Then
        Truth.assertThat(actualPlaylists).isEqualTo(expectedPlaylists)

    }

    private fun getTestPlaylistsEntity() =
        PlaylistsEntity(
            "some href",
            getTestPlaylistEntityList(),
            20,
            "some next",
            0,
            null,
            35
        )

    private fun getTestPlaylistEntityList(): List<PlaylistEntity> =
        listOf(
            PlaylistEntity("id 1", "name 1", getTestIconEntityList()),
            PlaylistEntity("id 2", "name 2", getTestIconEntityList())
        )

    private fun getTestIconEntityList(): List<IconEntity> {
        return listOf(
            IconEntity(200, 100, "url 1"),
            IconEntity(300, 400, "url 2")
        )
    }

    private fun getTestPlaylists() =
        listOf(
            Playlist("id 1", "name 1", getTestIconList()),
            Playlist("id 2", "name 2", getTestIconList())
        )

    private fun getTestIconList(): List<Icon> {
        return listOf(Icon(200, 100, "url 1"), Icon(300, 400, "url 2"))
    }

}