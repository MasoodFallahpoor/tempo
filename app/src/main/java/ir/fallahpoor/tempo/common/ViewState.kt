package ir.fallahpoor.tempo.common

interface ViewState {

    data class DataLoaded<T>(val data: T) : ViewState
    data class Error(val errorMessage: String) : ViewState

}