package ir.fallahpoor.tempo.data.webservice.calladapter

import androidx.lifecycle.LiveData
import ir.fallahpoor.tempo.data.common.Error
import ir.fallahpoor.tempo.data.common.Resource
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.lang.reflect.Type

class LiveDataCallAdapter<T>(private val responseType: Type) :
    CallAdapter<T, LiveData<Resource<T>>> {

    private companion object {
        const val UNKNOWN_ERROR_MESSAGE = "Something weird happened"
        const val NO_INTERNET_CONNECTION_MESSAGE = "No Internet connection"
    }

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
                            val message =
                                if (response.message().isNullOrBlank()) {
                                    UNKNOWN_ERROR_MESSAGE
                                } else {
                                    response.message()
                                }
                            val error = Error(message)
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
                        val message = when (t) {
                            is IOException -> NO_INTERNET_CONNECTION_MESSAGE
                            else -> UNKNOWN_ERROR_MESSAGE
                        }
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