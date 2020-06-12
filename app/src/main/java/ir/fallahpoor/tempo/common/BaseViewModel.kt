package ir.fallahpoor.tempo.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BaseViewModel<T> : ViewModel() {

    private val viewStateLiveData = MutableLiveData<ViewState<T>>()
    private val disposables = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        if (!disposables.isDisposed) {
            disposables.clear()
        }
    }

    protected fun addDisposable(d: Disposable) {
        disposables.add(d)
    }

    protected fun setViewState(viewState: ViewState<T>) {
        viewStateLiveData.value = viewState
    }

    fun getViewState(): LiveData<ViewState<T>> = viewStateLiveData

}