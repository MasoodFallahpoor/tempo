package ir.fallahpoor.tempo.data.repository.category

import io.reactivex.Single
import ir.fallahpoor.tempo.data.entity.category.CategoriesEnvelop
import ir.fallahpoor.tempo.data.entity.category.CategoryEntity
import ir.fallahpoor.tempo.data.entity.common.ListEntity
import ir.fallahpoor.tempo.data.entity.playlist.PlaylistEntity
import ir.fallahpoor.tempo.data.entity.playlist.PlaylistsEnvelop
import ir.fallahpoor.tempo.data.webservice.CategoriesWebService
import javax.inject.Inject

class CategoriesRepositoryImpl(
    private val categoriesWebService: CategoriesWebService
) : CategoriesRepository {

    override fun getCategories(offset: Int, limit: Int): Single<ListEntity<CategoryEntity>> {
        return categoriesWebService.getCategories(offset, limit)
            .map { t: CategoriesEnvelop -> t.categoriesEntity }
    }

    override fun getPlaylists(
        categoryId: String,
        offset: Int,
        limit: Int
    ): Single<ListEntity<PlaylistEntity>> {
        return categoriesWebService.getPlaylists(categoryId, offset, limit)
            .map { t: PlaylistsEnvelop -> t.playlistsEntity }
    }

}