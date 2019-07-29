package ir.fallahpoor.tempo.browse.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ir.fallahpoor.tempo.browse.model.CategoriesDataMapper
import ir.fallahpoor.tempo.data.repository.category.CategoriesRepository
import javax.inject.Inject

class BrowseCategoriesViewModelFactory @Inject
constructor(
    private val categoriesRepository: CategoriesRepository,
    private val categoriesDataMapper: CategoriesDataMapper
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(viewModelClass: Class<T>): T {

        if (viewModelClass.isAssignableFrom(BrowseCategoriesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BrowseCategoriesViewModel(categoriesRepository, categoriesDataMapper) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class")
        }

    }

}