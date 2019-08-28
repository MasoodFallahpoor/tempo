package ir.fallahpoor.tempo.data.entity.category

import com.google.gson.annotations.SerializedName

data class CategoriesEntity(
    @SerializedName("href")
    val href: String,
    @SerializedName("items")
    val items: List<CategoryEntity>,
    @SerializedName("limit")
    val limit: Int,
    @SerializedName("next")
    val next: String?,
    @SerializedName("offset")
    val offset: Int,
    @SerializedName("previous")
    val previous: String?,
    @SerializedName("total")
    val total: Int
)