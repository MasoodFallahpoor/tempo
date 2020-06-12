package ir.fallahpoor.tempo.search.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import ir.fallahpoor.tempo.common.*
import ir.fallahpoor.tempo.data.common.ExceptionHumanizer
import ir.fallahpoor.tempo.data.entity.SearchEntity
import ir.fallahpoor.tempo.data.repository.search.SearchRepository
import javax.inject.Inject

class SearchViewModel
@Inject constructor(
    private val searchRepository: SearchRepository
) : BaseViewModel<SearchEntity>() {

    private val searchResultLiveData = MutableLiveData<ViewState<SearchEntity>>()

    fun search(query: String) {

        setViewState(LoadingState())

        val d: Disposable = searchRepository.search(query)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { searchEntity: SearchEntity ->
                    setViewState(DataLoadedState(searchEntity))
                },
                { t: Throwable ->
                    val message: String = ExceptionHumanizer.getHumanizedErrorMessage(t)
                    setViewState(ErrorState(message))
                }
            )

        addDisposable(d)

    }

}
