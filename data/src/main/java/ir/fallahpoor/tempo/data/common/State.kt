package ir.fallahpoor.tempo.data.common

data class State(val status: Status, val message: String = "") {

    companion object {
        val LOADED = State(Status.LOADED)
        val LOADING = State(Status.LOADING)
        val LOADING_MORE = State(Status.LOADING_MORE)
    }

    enum class Status {
        LOADED,
        LOADING,
        ERROR,
        LOADING_MORE,
        ERROR_MORE
    }

}