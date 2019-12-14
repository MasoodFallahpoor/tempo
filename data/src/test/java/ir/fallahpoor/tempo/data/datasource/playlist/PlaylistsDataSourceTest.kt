package ir.fallahpoor.tempo.data.datasource.playlist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.paging.PositionalDataSource
import com.google.common.truth.Truth
import ir.fallahpoor.tempo.data.TestData
import ir.fallahpoor.tempo.data.common.ExceptionHumanizer
import ir.fallahpoor.tempo.data.common.State
import ir.fallahpoor.tempo.data.entity.playlist.PlaylistEntity
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
class PlaylistsDataSourceTest {

    companion object {
        private const val OFFSET = 0
        private const val LIMIT = 20
        private const val CATEGORY_ID = "12345"
    }

    @Rule
    @JvmField
    var rule: TestRule = InstantTaskExecutorRule()
    @Mock
    private lateinit var categoriesWebService: CategoriesWebService
    @Mock
    private lateinit var loadInitialCallback: PositionalDataSource.LoadInitialCallback<PlaylistEntity>
    @Mock
    private lateinit var loadRangeCallback: PositionalDataSource.LoadRangeCallback<PlaylistEntity>
    private lateinit var playlistsDataSource: PlaylistsDataSource
    private lateinit var loadInitialParams: PositionalDataSource.LoadInitialParams
    private lateinit var loadRangeParams: PositionalDataSource.LoadRangeParams

    @Before
    fun runBeforeEachTest() {
        playlistsDataSource = PlaylistsDataSource(categoriesWebService)
        playlistsDataSource.categoryId = CATEGORY_ID
        loadInitialParams = PositionalDataSource.LoadInitialParams(OFFSET, 0, LIMIT, true)
        loadRangeParams = PositionalDataSource.LoadRangeParams(OFFSET + LIMIT, LIMIT)
    }

    @Test
    fun loadInitial_happy_case() {

        // Given
        Mockito.`when`(categoriesWebService.getPlaylists(CATEGORY_ID, OFFSET, LIMIT))
            .thenReturn(Calls.response(Response.success(TestData.getTestPlaylistsEnvelop())))

        // When
        playlistsDataSource.loadInitial(loadInitialParams, loadInitialCallback)
        val actualStateLiveData: LiveData<State> = playlistsDataSource.stateLiveData
        actualStateLiveData.observeForever { }

        // Then
        Mockito.verify(categoriesWebService).getPlaylists(CATEGORY_ID, OFFSET, LIMIT)
        Mockito.verify(loadInitialCallback).onResult(
            TestData.getTestPlaylistsEnvelop().playlistsEntity.items,
            0,
            TestData.getTestPlaylistsEnvelop().playlistsEntity.total
        )
        Truth.assertThat(actualStateLiveData.value).isEqualTo(State.LOADED)

    }

    @Test
    fun loadInitial_sad_case() {

        // Given
        Mockito.`when`(categoriesWebService.getPlaylists(CATEGORY_ID, OFFSET, LIMIT))
            .thenReturn(Calls.failure(Exception()))

        // When
        playlistsDataSource.loadInitial(loadInitialParams, loadInitialCallback)
        val actualStateLiveData: LiveData<State> = playlistsDataSource.stateLiveData
        actualStateLiveData.observeForever { }

        // Then
        Mockito.verify(categoriesWebService).getPlaylists(CATEGORY_ID, OFFSET, LIMIT)
        Mockito.verifyZeroInteractions(loadInitialCallback)
        val expectedState = State(State.Status.ERROR, ExceptionHumanizer.SOMETHING_WENT_WRONG)
        Truth.assertThat(actualStateLiveData.value).isEqualTo(expectedState)

    }

    @Test
    fun loadRange_happy_case() {

        // Given
        Mockito.`when`(categoriesWebService.getPlaylists(CATEGORY_ID, OFFSET + LIMIT, LIMIT))
            .thenReturn(Calls.response(Response.success(TestData.getTestPlaylistsEnvelop())))

        // When
        playlistsDataSource.loadRange(loadRangeParams, loadRangeCallback)
        val actualStateLiveData: LiveData<State> = playlistsDataSource.stateLiveData
        actualStateLiveData.observeForever { }

        // Then
        Mockito.verify(categoriesWebService).getPlaylists(CATEGORY_ID, OFFSET + LIMIT, LIMIT)
        Mockito.verify(loadRangeCallback).onResult(
            TestData.getTestPlaylistsEnvelop().playlistsEntity.items
        )
        Truth.assertThat(actualStateLiveData.value).isEqualTo(State.LOADED)

    }

    @Test
    fun loadRange_sad_case() {

        // Given
        Mockito.`when`(categoriesWebService.getPlaylists(CATEGORY_ID, OFFSET + LIMIT, LIMIT))
            .thenReturn(Calls.failure(Exception()))

        // When
        playlistsDataSource.loadRange(loadRangeParams, loadRangeCallback)
        val actualStateLiveData: LiveData<State> = playlistsDataSource.stateLiveData
        actualStateLiveData.observeForever { }

        // Then
        Mockito.verify(categoriesWebService).getPlaylists(CATEGORY_ID, OFFSET + LIMIT, LIMIT)
        Mockito.verifyZeroInteractions(loadRangeCallback)
        val expectedState = State(State.Status.ERROR_MORE, ExceptionHumanizer.SOMETHING_WENT_WRONG)
        Truth.assertThat(actualStateLiveData.value).isEqualTo(expectedState)

    }

}