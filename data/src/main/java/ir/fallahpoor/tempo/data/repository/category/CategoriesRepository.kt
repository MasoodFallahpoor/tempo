package ir.fallahpoor.tempo.data.repository.category

import ir.fallahpoor.tempo.data.entity.category.CategoryEntity
import ir.fallahpoor.tempo.data.entity.playlist.PlaylistEntity
import ir.fallahpoor.tempo.data.repository.ListResult

interface CategoriesRepository {

    fun getCategories(): ListResult<CategoryEntity>

    fun getPlaylists(categoryId: String): ListResult<PlaylistEntity>

}