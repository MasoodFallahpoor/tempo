package ir.fallahpoor.tempo.search.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import ir.fallahpoor.tempo.common.BaseViewModel
import ir.fallahpoor.tempo.common.DataLoadedState
import ir.fallahpoor.tempo.common.ErrorState
import ir.fallahpoor.tempo.common.LoadingState
import ir.fallahpoor.tempo.data.common.ExceptionHumanizer
import ir.fallahpoor.tempo.data.entity.SearchEntity
import ir.fallahpoor.tempo.data.repository.search.SearchRepository

class SearchViewModel
@ViewModelInject constructor(
    private val searchRepository: SearchRepository
) : BaseViewModel<SearchEntity>() {

    private var previousQuery: String? = null

    fun search(query: String) {

        if (query == previousQuery) {
            return
        }

        val d: Disposable = searchRepository.search(query)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                setViewState(LoadingState())
            }
            .subscribe(
                { searchEntity: SearchEntity ->
                    previousQuery = query
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
