package ir.fallahpoor.tempo.common.viewstate

class DataErrorViewState(private val message: String) :
    ViewState {

    fun getMessage() = message

}