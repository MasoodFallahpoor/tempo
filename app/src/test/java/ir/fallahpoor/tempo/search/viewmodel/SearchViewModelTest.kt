package ir.fallahpoor.tempo.search.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.common.truth.Truth
import ir.fallahpoor.tempo.TestData
import ir.fallahpoor.tempo.common.ViewState
import ir.fallahpoor.tempo.data.common.Resource
import ir.fallahpoor.tempo.data.entity.SearchEntity
import ir.fallahpoor.tempo.data.repository.search.SearchRepository
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
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
        searchViewModel = SearchViewModel(searchRepository)
    }

    @Test
    fun searchResultLiveData_is_updated_with_DataLoaded_state() {

        // Given
        val liveData = MutableLiveData<Resource<SearchEntity>>()
        liveData.value = Resource.Success(TestData.getSearchEntity())
        Mockito.`when`(searchRepository.search(SEARCH_QUERY)).thenReturn(liveData)

        // When
        val searchResultLiveData: LiveData<ViewState> = searchViewModel.searchResult
        searchResultLiveData.observeForever {
        }
        searchViewModel.search(SEARCH_QUERY)

        // Then
        Mockito.verify(searchRepository).search(SEARCH_QUERY)
        Truth.assertThat(searchResultLiveData.value).isInstanceOf(ViewState.DataLoaded::class.java)
        Truth.assertThat((searchResultLiveData.value as ViewState.DataLoaded<SearchEntity>).data)
            .isEqualTo(TestData.getSearchEntity())

    }

    @Test
    fun searchResultLiveData_is_updated_with_Error_state() {

        // Given
        val liveData = MutableLiveData<Resource<SearchEntity>>()
        liveData.value = Resource.Error(ERROR_MESSAGE)
        Mockito.`when`(searchRepository.search(SEARCH_QUERY)).thenReturn(liveData)

        // When
        val searchResultLiveData: LiveData<ViewState> = searchViewModel.searchResult
        searchResultLiveData.observeForever {
        }
        searchViewModel.search(SEARCH_QUERY)

        // Then
        Mockito.verify(searchRepository).search(SEARCH_QUERY)
        Truth.assertThat(searchResultLiveData.value).isInstanceOf(ViewState.Error::class.java)
        Truth.assertThat((searchResultLiveData.value as ViewState.Error).errorMessage)
            .isEqualTo(ERROR_MESSAGE)

    }

}