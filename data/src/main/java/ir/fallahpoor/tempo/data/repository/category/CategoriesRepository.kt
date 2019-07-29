package ir.fallahpoor.tempo.data.repository.category

import androidx.lifecycle.LiveData
import ir.fallahpoor.tempo.data.Resource
import ir.fallahpoor.tempo.data.entity.CategoriesEntity

interface CategoriesRepository {

    fun getCategories(limit: Int, offset: Int) : LiveData<Resource<CategoriesEntity>>

}