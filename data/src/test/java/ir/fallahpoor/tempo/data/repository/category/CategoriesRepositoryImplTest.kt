package ir.fallahpoor.tempo.data.repository.category

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.common.truth.Truth
import ir.fallahpoor.tempo.data.Resource
import ir.fallahpoor.tempo.data.entity.category.CategoriesEntity
import ir.fallahpoor.tempo.data.entity.category.CategoriesEnvelop
import ir.fallahpoor.tempo.data.entity.playlist.PlaylistsEntity
import ir.fallahpoor.tempo.data.entity.playlist.PlaylistsEnvelop
import ir.fallahpoor.tempo.data.webservice.CategoriesWebService
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CategoriesRepositoryImplTest {

    private companion object {
        const val LIMIT = 10
        const val OFFSET = 20
        const val CATEGORY_ID = "123456"
    }

    @Rule
    @JvmField
    var rule: TestRule = InstantTaskExecutorRule()
    private lateinit var categoriesRepositoryImpl: CategoriesRepositoryImpl
    @Mock
    private lateinit var categoriesWebService: CategoriesWebService

    @Before
    fun runBeforeEachTest() {
        categoriesRepositoryImpl = CategoriesRepositoryImpl(categoriesWebService)
    }

    @Test
    fun getCategories_happy_case() {

        // Given
        val expectedLiveData = MutableLiveData<Resource<CategoriesEnvelop>>()
        expectedLiveData.value = Resource(Resource.Status.SUCCESS, getTestCategoriesEnvelop(), null)
        Mockito.`when`(categoriesWebService.getCategories(LIMIT, OFFSET))
            .thenReturn(expectedLiveData)

        // When
        val actualLiveData: LiveData<Resource<CategoriesEntity>> =
            categoriesRepositoryImpl.getCategories(LIMIT, OFFSET)
        actualLiveData.observeForever {
        }

        // Then
        Mockito.verify(categoriesWebService).getCategories(LIMIT, OFFSET)
        Truth.assertThat(actualLiveData.value?.status).isEqualTo(Resource.Status.SUCCESS)
        Truth.assertThat(actualLiveData.value?.data)
            .isEqualTo(getTestCategoriesEnvelop().categoriesEntity)
        Truth.assertThat(actualLiveData.value?.error).isEqualTo(null)

    }

    @Test
    fun getCategories_sad_case() {

        // Given
        val error = ir.fallahpoor.tempo.data.Error("some message")
        val expectedLiveData = MutableLiveData<Resource<CategoriesEnvelop>>()
        expectedLiveData.value = Resource(Resource.Status.ERROR, null, error)
        Mockito.`when`(categoriesWebService.getCategories(LIMIT, OFFSET))
            .thenReturn(expectedLiveData)

        // When
        val actualLiveData: LiveData<Resource<CategoriesEntity>> =
            categoriesRepositoryImpl.getCategories(LIMIT, OFFSET)
        actualLiveData.observeForever {
        }

        // Then
        Mockito.verify(categoriesWebService).getCategories(LIMIT, OFFSET)
        Truth.assertThat(actualLiveData.value?.status).isEqualTo(Resource.Status.ERROR)
        Truth.assertThat(actualLiveData.value?.data).isEqualTo(null)
        Truth.assertThat(actualLiveData.value?.error).isEqualTo(error)

    }

    @Test
    fun getPlaylists_happy_case() {

        // Given
        val expectedLiveData = MutableLiveData<Resource<PlaylistsEnvelop>>()
        expectedLiveData.value = Resource(Resource.Status.SUCCESS, getTestPlaylistsEnvelop(), null)
        Mockito.`when`(categoriesWebService.getPlaylists(CATEGORY_ID, LIMIT, OFFSET))
            .thenReturn(expectedLiveData)

        // When
        val actualLiveData: LiveData<Resource<PlaylistsEntity>> =
            categoriesRepositoryImpl.getPlaylists(CATEGORY_ID, LIMIT, OFFSET)
        actualLiveData.observeForever {
        }

        // Then
        Mockito.verify(categoriesWebService).getPlaylists(CATEGORY_ID, LIMIT, OFFSET)
        Truth.assertThat(actualLiveData.value?.status).isEqualTo(Resource.Status.SUCCESS)
        Truth.assertThat(actualLiveData.value?.data)
            .isEqualTo(getTestPlaylistsEnvelop().playlistsEntity)
        Truth.assertThat(actualLiveData.value?.error).isEqualTo(null)

    }

    @Test
    fun getPlaylists_sad_case() {

        // Given
        val error = ir.fallahpoor.tempo.data.Error("some message")
        val expectedLiveData = MutableLiveData<Resource<PlaylistsEnvelop>>()
        expectedLiveData.value = Resource(Resource.Status.ERROR, null, error)
        Mockito.`when`(categoriesWebService.getPlaylists(CATEGORY_ID, LIMIT, OFFSET))
            .thenReturn(expectedLiveData)

        // When
        val actualLiveData: LiveData<Resource<PlaylistsEntity>> =
            categoriesRepositoryImpl.getPlaylists(CATEGORY_ID, LIMIT, OFFSET)
        actualLiveData.observeForever {
        }

        // Then
        Mockito.verify(categoriesWebService).getPlaylists(CATEGORY_ID, LIMIT, OFFSET)
        Truth.assertThat(actualLiveData.value?.status).isEqualTo(Resource.Status.ERROR)
        Truth.assertThat(actualLiveData.value?.data).isEqualTo(null)
        Truth.assertThat(actualLiveData.value?.error).isEqualTo(error)

    }

    private fun getTestCategoriesEnvelop() =
        CategoriesEnvelop(
            CategoriesEntity(
                "https://api.spotify.com/v1/browse/categories?offset=0&limit=20",
                ArrayList(),
                20,
                "https://api.spotify.com/v1/browse/categories?offset=20&limit=20",
                0,
                null,
                35
            )
        )

    private fun getTestPlaylistsEnvelop() =
        PlaylistsEnvelop(
            PlaylistsEntity(
                "https://api.spotify.com/v1/browse/categories/rap/playlist?offset=0&limit=20",
                ArrayList(),
                20,
                "https://api.spotify.com/v1/browse/categories/rap/playlist?offset=20&limit=20",
                0,
                null,
                35
            )
        )

}