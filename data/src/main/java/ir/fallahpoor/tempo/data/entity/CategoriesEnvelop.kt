package ir.fallahpoor.tempo.data.entity

import com.google.gson.annotations.SerializedName

data class CategoriesEnvelop(
    @SerializedName("categories")
    val categoriesEntity: CategoriesEntity
)