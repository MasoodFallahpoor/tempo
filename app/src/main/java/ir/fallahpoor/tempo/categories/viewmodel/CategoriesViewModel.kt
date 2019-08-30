package ir.fallahpoor.tempo.categories.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import ir.fallahpoor.tempo.categories.model.CategoriesDataMapper
import ir.fallahpoor.tempo.common.viewstate.*
import ir.fallahpoor.tempo.data.common.Resource
import ir.fallahpoor.tempo.data.entity.common.ListEntity
import ir.fallahpoor.tempo.data.entity.category.CategoryEntity
import ir.fallahpoor.tempo.data.repository.category.CategoriesRepository

class CategoriesViewModel(
    private val categoriesRepository: CategoriesRepository,
    private val categoriesDataMapper: CategoriesDataMapper
) : ViewModel() {

    private companion object {
        const val LIMIT = 20
    }

    private var totalCount = 0
    private var offset = 0
    private var categoriesLiveData: LiveData<Resource<ListEntity<CategoryEntity>>>? = null
    private var moreCategoriesLiveData: LiveData<Resource<ListEntity<CategoryEntity>>>? = null
    private val viewStateLiveData = MutableLiveData<ViewState>()
    private val categoriesObserver = Observer { resource: Resource<ListEntity<CategoryEntity>> ->
        viewStateLiveData.value =
            if (resource.status == Resource.Status.SUCCESS) {
                totalCount = (resource.data?.total ?: 0)
                DataLoadedViewState(
                    categoriesDataMapper.map(
                        resource.data!!
                    )
                )
            } else {
                DataErrorViewState(resource.error!!.message)
            }
    }
    private val moreCategoriesObserver =
        Observer { resource: Resource<ListEntity<CategoryEntity>> ->
            viewStateLiveData.value =
                if (resource.status == Resource.Status.SUCCESS) {
                    offset += LIMIT
                    MoreDataLoadedViewState(
                        categoriesDataMapper.map(
                            resource.data!!
                        )
                    )
                } else {
                    MoreDataErrorViewState(resource.error!!.message)
                }
        }

    fun getCategories() {
        if (viewStateLiveData.value == null || viewStateLiveData.value is DataErrorViewState) {
            offset = 0
            viewStateLiveData.value = LoadingViewState()
            categoriesLiveData = categoriesRepository.getCategories(LIMIT, offset)
            categoriesLiveData?.observeForever(categoriesObserver)
        }
    }

    fun getViewStateLiveData() = viewStateLiveData

    override fun onCleared() {
        super.onCleared()
        categoriesLiveData?.removeObserver(categoriesObserver)
        moreCategoriesLiveData?.removeObserver(moreCategoriesObserver)
    }

    fun getMoreCategories() {
        if (offset < totalCount) {
            viewStateLiveData.value = LoadingViewState()
            moreCategoriesLiveData = categoriesRepository.getCategories(LIMIT, offset + LIMIT)
            moreCategoriesLiveData?.observeForever(moreCategoriesObserver)
        }
    }

}