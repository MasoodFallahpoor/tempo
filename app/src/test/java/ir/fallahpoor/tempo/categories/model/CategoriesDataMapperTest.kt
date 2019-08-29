package ir.fallahpoor.tempo.categories.model

import com.google.common.truth.Truth
import ir.fallahpoor.tempo.data.entity.common.IconEntity
import ir.fallahpoor.tempo.data.entity.category.CategoriesEntity
import ir.fallahpoor.tempo.data.entity.category.CategoryEntity
import org.junit.Test

class CategoriesDataMapperTest {

    private val categoriesDataMapper = CategoriesDataMapper()

    @Test
    fun test_map() {

        // Given
        val expectedCategories: List<Category> = getTestCategories()

        // When
        val actualCategories: List<Category> = categoriesDataMapper.map(getTestCategoriesEntity())

        // Then
        Truth.assertThat(actualCategories).isEqualTo(expectedCategories)

    }

    private fun getTestCategoriesEntity() =
        CategoriesEntity(
            "https://api.spotify.com/v1/browse/categories?offset=0&limit=20",
            getTestCategoryEntityList(),
            20,
            "https://api.spotify.com/v1/browse/categories?offset=20&limit=20",
            0,
            null,
            35
        )

    private fun getTestCategoryEntityList(): List<CategoryEntity> =
        listOf(
            CategoryEntity("href 1", getTestIconEntityList(), "id 1", "name 1"),
            CategoryEntity("href 2", getTestIconEntityList(), "id 2", "name 2")
        )

    private fun getTestIconEntityList(): List<IconEntity> {
        return listOf(
            IconEntity(200, 100, "url 1"),
            IconEntity(300, 400, "url 2")
        )
    }

    private fun getTestCategories() =
        listOf(
            Category("href 1", getTestIconList(), "id 1", "name 1"),
            Category("href 2", getTestIconList(), "id 2", "name 2")
        )

    private fun getTestIconList(): List<Icon> {
        return listOf(Icon(200, 100, "url 1"), Icon(300, 400, "url 2"))
    }

}