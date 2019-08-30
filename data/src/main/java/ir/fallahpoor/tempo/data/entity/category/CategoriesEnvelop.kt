package ir.fallahpoor.tempo.data.entity.category

import com.google.gson.annotations.SerializedName
import ir.fallahpoor.tempo.data.entity.common.ListEntity

data class CategoriesEnvelop(
    @SerializedName("categories")
    val categoriesEntity: ListEntity<CategoryEntity>
)