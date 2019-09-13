package ir.fallahpoor.tempo.data.repository.category

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.google.common.truth.Truth
import ir.fallahpoor.tempo.data.common.State
import ir.fallahpoor.tempo.data.datasource.category.CategoriesDataSource
import ir.fallahpoor.tempo.data.datasource.category.CategoriesDataSourceFactory
import ir.fallahpoor.tempo.data.datasource.playlist.PlaylistsDataSource
import ir.fallahpoor.tempo.data.datasource.playlist.PlaylistsDataSourceFactory
import ir.fallahpoor.tempo.data.entity.category.CategoriesEnvelop
import ir.fallahpoor.tempo.data.entity.category.CategoryEntity
import ir.fallahpoor.tempo.data.entity.common.IconEntity
import ir.fallahpoor.tempo.data.entity.common.ListEntity
import ir.fallahpoor.tempo.data.entity.playlist.PlaylistEntity
import ir.fallahpoor.tempo.data.entity.playlist.PlaylistsEnvelop
import ir.fallahpoor.tempo.data.repository.ListResult
import ir.fallahpoor.tempo.data.webservice.CategoriesWebService
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response
import retrofit2.mock.Calls

@RunWith(MockitoJUnitRunner::class)
class CategoriesRepositoryImplTest {

    private companion object {
        const val OFFSET = 0
        const val LIMIT = 20
        const val CATEGORY_ID = "123456"
    }

    @Rule
    @JvmField
    var rule: TestRule = InstantTaskExecutorRule()
    @Mock
    private lateinit var categoriesWebService: CategoriesWebService
    private lateinit var categoriesDataSourceFactory: CategoriesDataSourceFactory
    private lateinit var categoriesDataSource: CategoriesDataSource
    private lateinit var playlistsDataSourceFactory: PlaylistsDataSourceFactory
    private lateinit var playlistsDataSource: PlaylistsDataSource
    private lateinit var categoriesRepositoryImpl: CategoriesRepositoryImpl

    @Before
    fun runBeforeEachTest() {

        categoriesDataSource = CategoriesDataSource(categoriesWebService)
        categoriesDataSourceFactory = CategoriesDataSourceFactory(categoriesDataSource)

        playlistsDataSource = PlaylistsDataSource(categoriesWebService)
        playlistsDataSourceFactory = PlaylistsDataSourceFactory(playlistsDataSource)

        categoriesRepositoryImpl = CategoriesRepositoryImpl(
            categoriesDataSourceFactory,
            playlistsDataSourceFactory
        )

    }

    @Test
    fun getCategories_happy_case() {

        // given
        Mockito.`when`(categoriesWebService.getCategories(OFFSET, LIMIT))
            .thenReturn(Calls.response(Response.success(getTestCategoriesEnvelop())))

        // when
        val actualResult: ListResult<CategoryEntity> = categoriesRepositoryImpl.getCategories()
        val actualDataLiveData: LiveData<PagedList<CategoryEntity>> = actualResult.data
        val actualStateLiveData: LiveData<State> = actualResult.state
        actualDataLiveData.observeForever { }
        actualStateLiveData.observeForever { }

        // then
        Mockito.verify(categoriesWebService).getCategories(OFFSET, LIMIT)
        Truth.assertThat(actualDataLiveData.value).isInstanceOf(PagedList::class.java)
        Truth.assertThat(actualStateLiveData.value).isEqualTo(State.LOADED)

    }

    @Test
    fun getCategories_sad_case() {

        // given
        Mockito.`when`(categoriesWebService.getCategories(OFFSET, LIMIT))
            .thenReturn(Calls.failure(Exception()))

        // when
        val actualResult: ListResult<CategoryEntity> = categoriesRepositoryImpl.getCategories()
        val actualDataLiveData: LiveData<PagedList<CategoryEntity>> = actualResult.data
        val actualStateLiveData: LiveData<State> = actualResult.state
        actualDataLiveData.observeForever { }
        actualStateLiveData.observeForever { }

        // then
        Mockito.verify(categoriesWebService).getCategories(OFFSET, LIMIT)
        Truth.assertThat(actualDataLiveData.value).isEmpty()
        val expectedState = State(State.Status.ERROR, "Something went wrong")
        Truth.assertThat(actualStateLiveData.value).isEqualTo(expectedState)

    }

    @Test
    fun getPlaylists_happy_case() {

        // given
        Mockito.`when`(categoriesWebService.getPlaylists(CATEGORY_ID, OFFSET, LIMIT))
            .thenReturn(Calls.response(Response.success(getTestPlaylistsEnvelop())))

        // when
        val actualResult: ListResult<PlaylistEntity> =
            categoriesRepositoryImpl.getPlaylists(CATEGORY_ID)
        val actualDataLiveData: LiveData<PagedList<PlaylistEntity>> = actualResult.data
        val actualStateLiveData: LiveData<State> = actualResult.state
        actualDataLiveData.observeForever { }
        actualStateLiveData.observeForever { }

        // then
        Mockito.verify(categoriesWebService).getPlaylists(CATEGORY_ID, OFFSET, LIMIT)
        Truth.assertThat(actualDataLiveData.value).isInstanceOf(PagedList::class.java)
        Truth.assertThat(actualStateLiveData.value).isEqualTo(State.LOADED)

    }

    @Test
    fun getPlaylists_sad_case() {

        // given
        Mockito.`when`(categoriesWebService.getPlaylists(CATEGORY_ID, OFFSET, LIMIT))
            .thenReturn(Calls.failure(Exception()))

        // when
        val actualResult: ListResult<PlaylistEntity> =
            categoriesRepositoryImpl.getPlaylists(CATEGORY_ID)
        val actualDataLiveData: LiveData<PagedList<PlaylistEntity>> = actualResult.data
        val actualStateLiveData: LiveData<State> = actualResult.state
        actualDataLiveData.observeForever { }
        actualStateLiveData.observeForever { }

        // then
        Mockito.verify(categoriesWebService).getPlaylists(CATEGORY_ID, OFFSET, LIMIT)
        Truth.assertThat(actualDataLiveData.value).isEmpty()
        val expectedState = State(State.Status.ERROR, "Something went wrong")
        Truth.assertThat(actualStateLiveData.value).isEqualTo(expectedState)

    }

    private fun getTestCategoriesEnvelop() =
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

    private fun getTestPlaylistsEnvelop() =
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

    private fun getTestIconEntityList(): List<IconEntity> =
        listOf(
            IconEntity(100, 100, "some url"),
            IconEntity(200, 200, "some other url")
        )

}