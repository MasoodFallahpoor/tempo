package ir.fallahpoor.tempo.data.repository.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import ir.fallahpoor.tempo.data.common.ExceptionHumanizer
import ir.fallahpoor.tempo.data.common.Resource
import ir.fallahpoor.tempo.data.entity.SearchEntity
import ir.fallahpoor.tempo.data.webservice.SearchWebService
import retrofit2.Response

class SearchRepositoryImpl(private val searchWebService: SearchWebService) : SearchRepository {

    companion object {
        private const val OFFSET = 0
        private const val LIMIT = 20
        private const val TYPE = "artist,album,track,playlist"
    }

    override fun search(query: String): LiveData<Resource<SearchEntity>> =
        liveData {
            val resource: Resource<SearchEntity> =
                try {
                    val response: Response<SearchEntity> =
                        searchWebService.search(query, TYPE, OFFSET, LIMIT)
                    if (response.isSuccessful) {
                        Resource.Success(response.body()!!)
                    } else {
                        Resource.Error(response.message())
                    }
                } catch (t: Throwable) {
                    Resource.Error(ExceptionHumanizer.getHumanizedErrorMessage(t))
                }
            emit(resource)
        }

}