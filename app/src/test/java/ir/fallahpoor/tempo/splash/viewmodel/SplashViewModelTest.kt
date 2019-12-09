package ir.fallahpoor.tempo.splash.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.common.truth.Truth
import ir.fallahpoor.tempo.common.ViewState
import ir.fallahpoor.tempo.data.common.Resource
import ir.fallahpoor.tempo.data.repository.authentication.AuthenticationRepository
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SplashViewModelTest {

    companion object {
        private const val ERROR_MESSAGE = "error message"
    }

    @Rule
    @JvmField
    val rule: TestRule = InstantTaskExecutorRule()
    @Mock
    private lateinit var authenticationRepository: AuthenticationRepository
    private lateinit var splashViewModel: SplashViewModel

    @Before
    fun runBeforeEachTest() {
        splashViewModel = SplashViewModel(authenticationRepository)
    }

    @Test
    fun accessToken_LiveData_is_updated_with_DataLoaded_state() {

        // Given
        val liveData = MutableLiveData<Resource<Unit>>()
        liveData.value = Resource.Success(Unit)
        Mockito.`when`(authenticationRepository.getAccessToken()).thenReturn(liveData)

        // When
        val viewStateLiveData: LiveData<ViewState> = splashViewModel.getAccessToken()
        viewStateLiveData.observeForever {
        }

        // Then
        Mockito.verify(authenticationRepository).getAccessToken()
        Truth.assertThat(viewStateLiveData.value).isInstanceOf(ViewState.DataLoaded::class.java)

    }

    @Test
    fun accessToken_LiveData_is_updated_with_Error_state() {

        // Given
        val liveData = MutableLiveData<Resource<Unit>>()
        liveData.value = Resource.Error(ERROR_MESSAGE)
        Mockito.`when`(authenticationRepository.getAccessToken()).thenReturn(liveData)

        // When
        val viewStateLiveData: LiveData<ViewState> = splashViewModel.getAccessToken()
        viewStateLiveData.observeForever {
        }

        // Then
        Mockito.verify(authenticationRepository).getAccessToken()
        Truth.assertThat(viewStateLiveData.value).isInstanceOf(ViewState.Error::class.java)
        Truth.assertThat((viewStateLiveData.value as ViewState.Error).errorMessage)
            .isEqualTo(ERROR_MESSAGE)

    }

}