package ir.fallahpoor.tempo.data.repository.category

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import ir.fallahpoor.tempo.data.datasource.category.CategoriesDataSourceFactory
import ir.fallahpoor.tempo.data.datasource.playlist.PlaylistsDataSourceFactory
import ir.fallahpoor.tempo.data.entity.category.CategoryEntity
import ir.fallahpoor.tempo.data.entity.playlist.PlaylistEntity
import ir.fallahpoor.tempo.data.repository.ListResult
import javax.inject.Inject

class CategoriesRepositoryImpl
@Inject constructor(
    private val categoriesDataSourceFactory: CategoriesDataSourceFactory,
    private val playlistsDataSourceFactory: PlaylistsDataSourceFactory
) : CategoriesRepository {

    private val pagedListConfig = PagedList.Config.Builder()
        .setPrefetchDistance(10)
        .setPageSize(20)
        .build()

    override fun getCategories(): ListResult<CategoryEntity> {

        val categoriesLiveData: LiveData<PagedList<CategoryEntity>> =
            LivePagedListBuilder(categoriesDataSourceFactory, pagedListConfig)
                .build()

        return ListResult(
            categoriesLiveData,
            categoriesDataSourceFactory.stateLiveData
        )

    }

    override fun getPlaylists(categoryId: String): ListResult<PlaylistEntity> {

        playlistsDataSourceFactory.categoryId = categoryId

        val playlistsLiveData: LiveData<PagedList<PlaylistEntity>> =
            LivePagedListBuilder(playlistsDataSourceFactory, pagedListConfig)
                .build()

        return ListResult(
            playlistsLiveData,
            playlistsDataSourceFactory.stateLiveData
        )

    }

}