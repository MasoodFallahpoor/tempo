package ir.fallahpoor.tempo.categories.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ir.fallahpoor.tempo.categories.model.CategoriesDataMapper
import ir.fallahpoor.tempo.data.repository.category.CategoriesRepository
import javax.inject.Inject

class CategoriesViewModelFactory @Inject
constructor(
    private val categoriesRepository: CategoriesRepository,
    private val categoriesDataMapper: CategoriesDataMapper
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(viewModelClass: Class<T>): T {

        if (viewModelClass.isAssignableFrom(CategoriesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CategoriesViewModel(categoriesRepository, categoriesDataMapper) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class")
        }

    }

}