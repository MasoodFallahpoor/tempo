package ir.fallahpoor.tempo.data.datasource.category

import androidx.paging.DataSource
import ir.fallahpoor.tempo.data.entity.category.CategoryEntity
import javax.inject.Inject

class CategoriesDataSourceFactory
@Inject constructor(
    private val categoriesDataSource: CategoriesDataSource
) : DataSource.Factory<Int, CategoryEntity>() {

    override fun create(): DataSource<Int, CategoryEntity> = categoriesDataSource

    val stateLiveData = categoriesDataSource.stateLiveData

}