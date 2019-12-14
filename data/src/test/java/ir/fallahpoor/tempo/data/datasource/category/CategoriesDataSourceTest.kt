package ir.fallahpoor.tempo.data.datasource.category

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.paging.PositionalDataSource
import com.google.common.truth.Truth
import ir.fallahpoor.tempo.data.TestData
import ir.fallahpoor.tempo.data.common.ExceptionHumanizer
import ir.fallahpoor.tempo.data.common.State
import ir.fallahpoor.tempo.data.entity.category.CategoryEntity
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
class CategoriesDataSourceTest {

    companion object {
        private const val OFFSET = 0
        private const val LIMIT = 20
    }

    @Rule
    @JvmField
    var rule: TestRule = InstantTaskExecutorRule()
    @Mock
    private lateinit var categoriesWebService: CategoriesWebService
    @Mock
    private lateinit var loadInitialCallback: PositionalDataSource.LoadInitialCallback<CategoryEntity>
    @Mock
    private lateinit var loadRangeCallback: PositionalDataSource.LoadRangeCallback<CategoryEntity>
    private lateinit var categoriesDataSource: CategoriesDataSource
    private lateinit var loadInitialParams: PositionalDataSource.LoadInitialParams
    private lateinit var loadRangeParams: PositionalDataSource.LoadRangeParams

    @Before
    fun runBeforeEachTest() {
        categoriesDataSource = CategoriesDataSource(categoriesWebService)
        loadInitialParams = PositionalDataSource.LoadInitialParams(OFFSET, 0, LIMIT, true)
        loadRangeParams = PositionalDataSource.LoadRangeParams(OFFSET + LIMIT, LIMIT)
    }

    @Test
    fun loadInitial_happy_case() {

        // Given
        Mockito.`when`(categoriesWebService.getCategories(OFFSET, LIMIT))
            .thenReturn(Calls.response(Response.success(TestData.getTestCategoriesEnvelop())))

        // When
        categoriesDataSource.loadInitial(loadInitialParams, loadInitialCallback)
        val actualStateLiveData: LiveData<State> = categoriesDataSource.stateLiveData
        actualStateLiveData.observeForever { }

        // Then
        Mockito.verify(categoriesWebService).getCategories(OFFSET, LIMIT)
        Mockito.verify(loadInitialCallback).onResult(
            TestData.getTestCategoriesEnvelop().categoriesEntity.items,
            0,
            TestData.getTestCategoriesEnvelop().categoriesEntity.total
        )
        Truth.assertThat(actualStateLiveData.value).isEqualTo(State.LOADED)

    }

    @Test
    fun loadInitial_sad_case() {

        // Given
        Mockito.`when`(categoriesWebService.getCategories(OFFSET, LIMIT))
            .thenReturn(Calls.failure(Exception()))

        // When
        categoriesDataSource.loadInitial(loadInitialParams, loadInitialCallback)
        val actualStateLiveData: LiveData<State> = categoriesDataSource.stateLiveData
        actualStateLiveData.observeForever { }

        // Then
        Mockito.verify(categoriesWebService).getCategories(OFFSET, LIMIT)
        Mockito.verifyZeroInteractions(loadInitialCallback)
        val expectedState = State(State.Status.ERROR, ExceptionHumanizer.SOMETHING_WENT_WRONG)
        Truth.assertThat(actualStateLiveData.value).isEqualTo(expectedState)

    }

    @Test
    fun loadRange_happy_case() {

        // Given
        Mockito.`when`(categoriesWebService.getCategories(OFFSET + LIMIT, LIMIT))
            .thenReturn(Calls.response(Response.success(TestData.getTestCategoriesEnvelop())))

        // When
        categoriesDataSource.loadRange(loadRangeParams, loadRangeCallback)
        val actualStateLiveData: LiveData<State> = categoriesDataSource.stateLiveData
        actualStateLiveData.observeForever { }

        // Then
        Mockito.verify(categoriesWebService).getCategories(OFFSET + LIMIT, LIMIT)
        Mockito.verify(loadRangeCallback).onResult(
            TestData.getTestCategoriesEnvelop().categoriesEntity.items
        )
        Truth.assertThat(actualStateLiveData.value).isEqualTo(State.LOADED)

    }

    @Test
    fun loadRange_sad_case() {

        // Given
        Mockito.`when`(categoriesWebService.getCategories(OFFSET + LIMIT, LIMIT))
            .thenReturn(Calls.failure(Exception()))

        // When
        categoriesDataSource.loadRange(loadRangeParams, loadRangeCallback)
        val actualStateLiveData: LiveData<State> = categoriesDataSource.stateLiveData
        actualStateLiveData.observeForever { }

        // Then
        Mockito.verify(categoriesWebService).getCategories(OFFSET + LIMIT, LIMIT)
        Mockito.verifyZeroInteractions(loadRangeCallback)
        val expectedState = State(State.Status.ERROR_MORE, ExceptionHumanizer.SOMETHING_WENT_WRONG)
        Truth.assertThat(actualStateLiveData.value).isEqualTo(expectedState)

    }

}