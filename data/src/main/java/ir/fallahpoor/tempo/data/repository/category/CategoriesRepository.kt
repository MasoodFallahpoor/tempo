package ir.fallahpoor.tempo.data.repository.category

import androidx.lifecycle.LiveData
import ir.fallahpoor.tempo.data.common.Resource
import ir.fallahpoor.tempo.data.entity.common.ListEntity
import ir.fallahpoor.tempo.data.entity.category.CategoryEntity
import ir.fallahpoor.tempo.data.entity.playlist.PlaylistEntity

interface CategoriesRepository {

    fun getCategories(limit: Int, offset: Int): LiveData<Resource<ListEntity<CategoryEntity>>>

    fun getPlaylists(
        categoryId: String,
        limit: Int,
        offset: Int
    ): LiveData<Resource<ListEntity<PlaylistEntity>>>

}