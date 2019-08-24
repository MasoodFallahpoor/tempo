package ir.fallahpoor.tempo.data.webservice.calladapter

import androidx.lifecycle.LiveData
import ir.fallahpoor.tempo.data.Error
import ir.fallahpoor.tempo.data.Resource
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type

class LiveDataCallAdapter<T>(private val responseType: Type) :
    CallAdapter<T, LiveData<Resource<T>>> {

    override fun adapt(call: Call<T>): LiveData<Resource<T>> {

        return object : LiveData<Resource<T>>() {
            private var isSuccess = false

            override fun onActive() {
                super.onActive()
                if (!isSuccess) enqueue()
            }

            override fun onInactive() {
                super.onInactive()
                dequeue()
            }

            private fun dequeue() {
                if (call.isExecuted) call.cancel()
            }

            private fun enqueue() {
                call.enqueue(object : Callback<T> {
                    override fun onResponse(call: Call<T>, response: Response<T>) {
                        isSuccess = if (response.isSuccessful) {
                            postValue(
                                Resource(
                                    Resource.Status.SUCCESS,
                                    response.body(),
                                    null
                                )
                            )
                            true
                        } else {
                            val error = Error(response.message())
                            postValue(
                                Resource(
                                    Resource.Status.ERROR,
                                    null,
                                    error
                                )
                            )
                            false
                        }

                    }

                    override fun onFailure(call: Call<T>, t: Throwable) {
                        val message = t.message ?: "Unknown error"
                        val error = Error(message)
                        postValue(
                            Resource(
                                Resource.Status.ERROR,
                                null,
                                error
                            )
                        )
                    }
                })
            }
        }

    }

    override fun responseType(): Type = responseType

}