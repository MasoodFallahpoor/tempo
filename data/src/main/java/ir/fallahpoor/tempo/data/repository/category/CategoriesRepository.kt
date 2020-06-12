package ir.fallahpoor.tempo.data.repository.category

import io.reactivex.Single
import ir.fallahpoor.tempo.data.entity.category.CategoriesEnvelop
import ir.fallahpoor.tempo.data.entity.category.CategoryEntity
import ir.fallahpoor.tempo.data.entity.common.ListEntity
import ir.fallahpoor.tempo.data.entity.playlist.PlaylistEntity

interface CategoriesRepository {

    fun getCategories(offset: Int, limit: Int): Single<ListEntity<CategoryEntity>>

    fun getPlaylists(categoryId: String, offset: Int, limit: Int): Single<ListEntity<PlaylistEntity>>

}