package ir.fallahpoor.tempo.search.viewmodel

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import ir.fallahpoor.tempo.common.BaseViewModel
import ir.fallahpoor.tempo.common.DataLoadedState
import ir.fallahpoor.tempo.common.ErrorState
import ir.fallahpoor.tempo.common.LoadingState
import ir.fallahpoor.tempo.data.common.ExceptionHumanizer
import ir.fallahpoor.tempo.data.entity.SearchEntity
import ir.fallahpoor.tempo.data.repository.search.SearchRepository
import javax.inject.Inject

class SearchViewModel
@Inject constructor(
    private val searchRepository: SearchRepository
) : BaseViewModel<SearchEntity>() {

    private var previousQuery: String? = null

    fun search(query: String) {

        if (query == previousQuery) {
            return
        }

        setViewState(LoadingState())

        val d: Disposable = searchRepository.search(query)
            .observeOn(AndroidSchedulers.mainThread())
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
