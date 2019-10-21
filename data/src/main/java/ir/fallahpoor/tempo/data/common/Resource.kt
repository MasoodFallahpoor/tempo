package ir.fallahpoor.tempo.data.common

sealed class Resource<T>(
    val status: Status,
    val data: T?,
    val errorMessage: String?
) {

    enum class Status {
        SUCCESS,
        ERROR
    }

    class Success<T>(data: T?) : Resource<T>(Status.SUCCESS, data, null)
    class Error<T>(errorMessage: String) : Resource<T>(Status.ERROR, null, errorMessage)

}