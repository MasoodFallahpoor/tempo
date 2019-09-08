package ir.fallahpoor.tempo.categories.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import ir.fallahpoor.tempo.data.common.State
import ir.fallahpoor.tempo.data.entity.category.CategoryEntity
import ir.fallahpoor.tempo.data.repository.ListResult
import ir.fallahpoor.tempo.data.repository.category.CategoriesRepository
import javax.inject.Inject

class CategoriesViewModel @Inject constructor(categoriesRepository: CategoriesRepository) :
    ViewModel() {

    private val categoriesResult: ListResult<CategoryEntity> = categoriesRepository.getCategories()

    val categories: LiveData<PagedList<CategoryEntity>> = categoriesResult.data
    val state: LiveData<State> = categoriesResult.state

}