package ir.fallahpoor.tempo.categories.model

import ir.fallahpoor.tempo.data.entity.IconEntity
import ir.fallahpoor.tempo.data.entity.category.CategoriesEntity
import ir.fallahpoor.tempo.data.entity.category.CategoryEntity
import javax.inject.Inject

class CategoriesDataMapper @Inject constructor() {

    fun map(categoriesEntity: CategoriesEntity): List<Category> =
        categoriesEntity.items.map { mapCategory(it) }

    private fun mapCategory(categoryEntity: CategoryEntity) =
        Category(
            categoryEntity.href,
            mapIcons(categoryEntity.icons),
            categoryEntity.id,
            categoryEntity.name
        )

    private fun mapIcons(iconsList: List<IconEntity>): List<Icon> =
        iconsList.map { Icon(it.width, it.height, it.url) }

}