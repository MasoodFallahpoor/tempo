package ir.fallahpoor.tempo.search.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import io.reactivex.Single
import ir.fallahpoor.tempo.TestData
import ir.fallahpoor.tempo.common.DataLoadedState
import ir.fallahpoor.tempo.common.ErrorState
import ir.fallahpoor.tempo.data.repository.search.SearchRepository
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@Config(manifest = Config.NONE)
@RunWith(RobolectricTestRunner::class)
class SearchViewModelTest {

    companion object {
        private const val SEARCH_QUERY = "search query"
        private const val ERROR_MESSAGE = "error message"
    }

    @Rule
    @JvmField
    val rule: TestRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var searchRepository: SearchRepository
    private lateinit var searchViewModel: SearchViewModel

    @Before
    fun runBeforeEachTest() {
        MockitoAnnotations.initMocks(this)
        searchViewModel = SearchViewModel(searchRepository)
    }

    @Test
    fun searchResultLiveData_is_updated_with_DataLoaded_state() {

        // Given
        Mockito.`when`(searchRepository.search(SEARCH_QUERY))
            .thenReturn(Single.just(TestData.getSearchEntity()))

        // When
        searchViewModel.search(SEARCH_QUERY)
        val searchResultLiveData = searchViewModel.getViewState()
        searchResultLiveData.observeForever {
        }

        // Then
        Mockito.verify(searchRepository).search(SEARCH_QUERY)
        Truth.assertThat(searchResultLiveData.value).isInstanceOf(DataLoadedState::class.java)
        Truth.assertThat((searchResultLiveData.value as DataLoadedState).data)
            .isEqualTo(TestData.getSearchEntity())

    }

    @Test
    fun searchResultLiveData_is_updated_with_Error_state() {

        // Given
        Mockito.`when`(searchRepository.search(SEARCH_QUERY)).thenReturn(
            Single.error(
                Throwable(
                    ERROR_MESSAGE
                )
            )
        )

        // When
        searchViewModel.search(SEARCH_QUERY)
        val searchResultLiveData = searchViewModel.getViewState()
        searchResultLiveData.observeForever {
        }

        // Then
        Mockito.verify(searchRepository).search(SEARCH_QUERY)
        Truth.assertThat(searchResultLiveData.value).isInstanceOf(ErrorState::class.java)

    }

}