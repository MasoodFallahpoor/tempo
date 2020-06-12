package ir.fallahpoor.tempo.common

sealed class ViewState<T>

class DataLoadedState<T>(val data: T) : ViewState<T>()
class ErrorState<T>(val errorMessage: String) : ViewState<T>()
class LoadingState<T> : ViewState<T>()