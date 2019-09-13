package ir.fallahpoor.tempo.data

import ir.fallahpoor.tempo.data.entity.category.CategoriesEnvelop
import ir.fallahpoor.tempo.data.entity.category.CategoryEntity
import ir.fallahpoor.tempo.data.entity.common.IconEntity
import ir.fallahpoor.tempo.data.entity.common.ListEntity
import ir.fallahpoor.tempo.data.entity.playlist.PlaylistEntity
import ir.fallahpoor.tempo.data.entity.playlist.PlaylistsEnvelop

object TestData {

    fun getTestCategoriesEnvelop() =
        CategoriesEnvelop(
            ListEntity(
                "https://api.spotify.com/v1/browse/categories?offset=0&limit=20",
                getTestCategoryEntityList(),
                20,
                "https://api.spotify.com/v1/browse/categories?offset=20&limit=20",
                0,
                null,
                1
            )
        )

    private fun getTestCategoryEntityList(): List<CategoryEntity> =
        listOf(
            CategoryEntity("some href", getTestIconEntityList(), "some id", "some name")
        )

    private fun getTestIconEntityList(): List<IconEntity> =
        listOf(
            IconEntity(100, 100, "some url"),
            IconEntity(200, 200, "some other url")
        )

    fun getTestPlaylistsEnvelop() =
        PlaylistsEnvelop(
            ListEntity(
                "https://api.spotify.com/v1/browse/categories/rap/playlist?offset=0&limit=20",
                getTestPlaylistEntityList(),
                20,
                "https://api.spotify.com/v1/browse/categories/rap/playlist?offset=20&limit=20",
                0,
                null,
                1
            )
        )

    private fun getTestPlaylistEntityList(): List<PlaylistEntity> =
        listOf(
            PlaylistEntity("some id", "some name", getTestIconEntityList())
        )

}