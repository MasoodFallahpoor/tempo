package ir.fallahpoor.tempo.common.viewstate

class MoreDataErrorViewState(private val message: String) :
    ViewState {

    fun getMessage() = message

}