package ir.fallahpoor.tempo.categories.viewmodel

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import ir.fallahpoor.tempo.common.BaseViewModel
import ir.fallahpoor.tempo.common.DataLoadedState
import ir.fallahpoor.tempo.common.ErrorState
import ir.fallahpoor.tempo.common.LoadingState
import ir.fallahpoor.tempo.data.common.ExceptionHumanizer
import ir.fallahpoor.tempo.data.entity.category.CategoryEntity
import ir.fallahpoor.tempo.data.entity.common.ListEntity
import ir.fallahpoor.tempo.data.repository.category.CategoriesRepository
import javax.inject.Inject

class CategoriesViewModel
@Inject constructor(
    private val categoriesRepository: CategoriesRepository
) : BaseViewModel<List<CategoryEntity>>() {

    private companion object {
        const val LIMIT = 20
    }

    private var categories = mutableListOf<CategoryEntity>()
    private var totalItems = Int.MAX_VALUE
    private var offset = 0

    fun getCategories() {
        if (categories.isEmpty()) {
            _getCategories()
        } else {
            setViewState(DataLoadedState(categories))
        }
    }

    fun getMoreCategories() {
        if (moreCategoriesAvailable()) {
            _getCategories()
        }
    }

    private fun _getCategories() {

        setViewState(LoadingState())

        val d: Disposable = categoriesRepository.getCategories(offset, LIMIT)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { c: ListEntity<CategoryEntity> ->
                    categories.addAll(c.items)
                    totalItems = c.total
                    offset += LIMIT
                    setViewState(DataLoadedState(categories))
                },
                { t: Throwable ->
                    val message: String = ExceptionHumanizer.getHumanizedErrorMessage(t)
                    setViewState(ErrorState(message))
                }
            )

        addDisposable(d)

    }

    private fun moreCategoriesAvailable() = offset < totalItems

}