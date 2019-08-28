package ir.fallahpoor.tempo.data.repository.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import ir.fallahpoor.tempo.data.Resource
import ir.fallahpoor.tempo.data.entity.category.CategoriesEntity
import ir.fallahpoor.tempo.data.entity.category.CategoriesEnvelop
import ir.fallahpoor.tempo.data.entity.playlist.PlaylistsEntity
import ir.fallahpoor.tempo.data.entity.playlist.PlaylistsEnvelop
import ir.fallahpoor.tempo.data.webservice.CategoriesWebService
import javax.inject.Inject

class CategoriesRepositoryImpl
@Inject constructor(
    private val categoryWebService: CategoriesWebService
) : CategoriesRepository {

    override fun getCategories(limit: Int, offset: Int): LiveData<Resource<CategoriesEntity>> {
        return Transformations.map(categoryWebService.getCategories(limit, offset), ::t1)
    }

    private fun t1(resource: Resource<CategoriesEnvelop>): Resource<CategoriesEntity> =
        if (resource.status == Resource.Status.SUCCESS) {
            Resource(Resource.Status.SUCCESS, resource.data?.categoriesEntity, null)
        } else {
            Resource(Resource.Status.ERROR, null, resource.error)
        }

    override fun getPlaylists(
        categoryId: String,
        limit: Int,
        offset: Int
    ): LiveData<Resource<PlaylistsEntity>> {
        return Transformations.map(categoryWebService.getPlaylists(categoryId, limit, offset), ::t2)
    }

    private fun t2(resource: Resource<PlaylistsEnvelop>): Resource<PlaylistsEntity> =
        if (resource.status == Resource.Status.SUCCESS) {
            Resource(Resource.Status.SUCCESS, resource.data?.playlistsEntity, null)
        } else {
            Resource(Resource.Status.ERROR, null, resource.error)
        }

}