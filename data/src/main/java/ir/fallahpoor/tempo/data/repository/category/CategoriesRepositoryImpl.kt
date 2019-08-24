package ir.fallahpoor.tempo.data.repository.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import ir.fallahpoor.tempo.data.Resource
import ir.fallahpoor.tempo.data.entity.CategoriesEntity
import ir.fallahpoor.tempo.data.entity.CategoriesEnvelop
import ir.fallahpoor.tempo.data.webservice.CategoriesWebService
import javax.inject.Inject

class CategoriesRepositoryImpl
@Inject constructor(
    private val categoryWebService: CategoriesWebService
) : CategoriesRepository {

    override fun getCategories(limit: Int, offset: Int): LiveData<Resource<CategoriesEntity>> =
        Transformations.map(categoryWebService.getCategories(limit, offset), ::transform)

    private fun transform(resource: Resource<CategoriesEnvelop>): Resource<CategoriesEntity> =
        if (resource.status == Resource.Status.SUCCESS) {
            Resource(Resource.Status.SUCCESS, resource.data?.categoriesEntity, null)
        } else {
            Resource(Resource.Status.ERROR, null, resource.error)
        }

}