package ir.fallahpoor.tempo.categories.viewmodel

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import ir.fallahpoor.tempo.common.*
import ir.fallahpoor.tempo.data.common.ExceptionHumanizer
import ir.fallahpoor.tempo.data.entity.category.CategoriesEnvelop
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

    private var totalItems = Int.MAX_VALUE
    private var offset = 0

    fun getCategories() {

        if (moreCategoriesAvailable()) {

            setViewState(LoadingState())

            val d: Disposable = categoriesRepository.getCategories(offset, LIMIT)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { c: ListEntity<CategoryEntity> ->
                        totalItems = c.total
                        offset += LIMIT
                        setViewState(DataLoadedState(c.items))
                    },
                    { t: Throwable ->
                        val message: String = ExceptionHumanizer.getHumanizedErrorMessage(t)
                        setViewState(ErrorState(message))
                    }
                )

            addDisposable(d)

        }

    }

    private fun moreCategoriesAvailable() = offset < totalItems

}