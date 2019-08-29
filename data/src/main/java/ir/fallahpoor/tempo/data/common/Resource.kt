package ir.fallahpoor.tempo.data.common

class Resource<T>(
    val status: Status,
    val data: T?,
    val error: Error?
) {

    enum class Status {
        SUCCESS,
        ERROR
    }

}