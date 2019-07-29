package ir.fallahpoor.tempo.data.entity

import com.google.gson.annotations.SerializedName

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