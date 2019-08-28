package ir.fallahpoor.tempo.data.entity.category

import com.google.gson.annotations.SerializedName
import ir.fallahpoor.tempo.data.entity.IconEntity

data class CategoryEntity(
    @SerializedName("href")
    val href: String,
    @SerializedName("icons")
    val icons: List<IconEntity>,
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String
)