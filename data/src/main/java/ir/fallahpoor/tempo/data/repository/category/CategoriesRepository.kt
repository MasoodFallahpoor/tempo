package ir.fallahpoor.tempo.data.repository.category

import androidx.lifecycle.LiveData
import ir.fallahpoor.tempo.data.Resource
import ir.fallahpoor.tempo.data.entity.category.CategoriesEntity
import ir.fallahpoor.tempo.data.entity.playlist.PlaylistsEntity

interface CategoriesRepository {

    fun getCategories(limit: Int, offset: Int): LiveData<Resource<CategoriesEntity>>

    fun getPlaylists(
        categoryId: String,
        limit: Int,
        offset: Int
    ): LiveData<Resource<PlaylistsEntity>>

}