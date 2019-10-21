package ir.fallahpoor.tempo.data.repository.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ir.fallahpoor.tempo.data.common.ExceptionHumanizer
import ir.fallahpoor.tempo.data.common.Resource
import ir.fallahpoor.tempo.data.entity.SearchEntity
import ir.fallahpoor.tempo.data.webservice.SearchWebService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchRepositoryImpl(private val searchWebService: SearchWebService) : SearchRepository {

    companion object {
        private const val OFFSET = 0
        private const val LIMIT = 20
        private const val TYPE = "artist,album,track,playlist"
    }

    override fun search(query: String): LiveData<Resource<SearchEntity>> {

        val liveData = MutableLiveData<Resource<SearchEntity>>()

        val call: Call<SearchEntity> = searchWebService.search(query, TYPE, OFFSET, LIMIT)
        call.enqueue(object : Callback<SearchEntity> {

            override fun onResponse(call: Call<SearchEntity>, response: Response<SearchEntity>) {

                if (response.isSuccessful) {
                    liveData.value = Resource.Success(response.body())
                } else {
                    liveData.value = Resource.Error(response.message())
                }

            }

            override fun onFailure(call: Call<SearchEntity>, t: Throwable) {
                liveData.value = Resource.Error(ExceptionHumanizer.getHumanizedErrorMessage(t))
            }

        })

        return liveData

    }

}