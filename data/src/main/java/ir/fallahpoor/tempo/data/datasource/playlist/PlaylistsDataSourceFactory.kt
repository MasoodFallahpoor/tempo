package ir.fallahpoor.tempo.data.datasource.playlist

import androidx.paging.DataSource
import ir.fallahpoor.tempo.data.entity.playlist.PlaylistEntity
import javax.inject.Inject

class PlaylistsDataSourceFactory
@Inject constructor(
    private val playlistsDataSource: PlaylistsDataSource
) : DataSource.Factory<Int, PlaylistEntity>() {

    override fun create(): DataSource<Int, PlaylistEntity> {
        playlistsDataSource.categoryId = categoryId
        return playlistsDataSource
    }

    var categoryId: String = ""
    val stateLiveData = playlistsDataSource.stateLiveData

}