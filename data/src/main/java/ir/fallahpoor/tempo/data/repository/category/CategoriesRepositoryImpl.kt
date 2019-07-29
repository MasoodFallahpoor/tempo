package ir.fallahpoor.tempo.data.repository.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ir.fallahpoor.tempo.data.Error
import ir.fallahpoor.tempo.data.Resource
import ir.fallahpoor.tempo.data.entity.CategoriesEntity
import ir.fallahpoor.tempo.data.entity.CategoriesEnvelop
import ir.fallahpoor.tempo.data.webservice.CategoriesWebService
import ir.fallahpoor.tempo.data.webservice.WebServiceFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class CategoriesRepositoryImpl
@Inject constructor(
    private val webServiceFactory: WebServiceFactory
) : CategoriesRepository {

    override fun getCategories(limit: Int, offset: Int): LiveData<Resource<CategoriesEntity>> {

        val liveData = MutableLiveData<Resource<CategoriesEntity>>()
        val categoryWebService =
            webServiceFactory.createApiService(CategoriesWebService::class.java)
        val categoriesCall: Call<CategoriesEnvelop> = categoryWebService.getCategories(limit, offset)

        categoriesCall.enqueue(object : Callback<CategoriesEnvelop> {

            override fun onFailure(call: Call<CategoriesEnvelop>, t: Throwable) {
                val error = Error(t.message!!)
                liveData.value = Resource(Resource.Status.ERROR, null, error)
            }

            override fun onResponse(
                call: Call<CategoriesEnvelop>,
                response: Response<CategoriesEnvelop>
            ) {
                liveData.value = if (response.isSuccessful) {
                    Resource(Resource.Status.SUCCESS, response.body()?.categoriesEntity, null)
                } else {
                    val error = Error(response.message())
                    Resource(Resource.Status.ERROR, null, error)
                }
            }

        })

        return liveData

    }

}