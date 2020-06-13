package ir.fallahpoor.tempo.splash.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import io.reactivex.Completable
import ir.fallahpoor.tempo.common.DataLoadedState
import ir.fallahpoor.tempo.common.ErrorState
import ir.fallahpoor.tempo.data.repository.authentication.AuthenticationRepository
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
        MockitoAnnotations.initMocks(this)
        splashViewModel = SplashViewModel(authenticationRepository)
    }

    @Test
    fun accessToken_LiveData_is_updated_with_DataLoaded_state() {

        // Given
        Mockito.`when`(authenticationRepository.getAccessToken()).thenReturn(Completable.complete())

        // When
        splashViewModel.getAccessToken()
        val viewStateLiveData = splashViewModel.getViewState()
        viewStateLiveData.observeForever {
        }

        // Then
        Mockito.verify(authenticationRepository).getAccessToken()
        Truth.assertThat(viewStateLiveData.value).isInstanceOf(DataLoadedState::class.java)

    }

    @Test
    fun accessToken_LiveData_is_updated_with_Error_state() {

        // Given
        Mockito.`when`(authenticationRepository.getAccessToken()).thenReturn(
            Completable.error(
                Throwable(ERROR_MESSAGE)
            )
        )

        // When
        splashViewModel.getAccessToken()
        val viewStateLiveData = splashViewModel.getViewState()
        viewStateLiveData.observeForever {
        }

        // Then
        Mockito.verify(authenticationRepository).getAccessToken()
        Truth.assertThat(viewStateLiveData.value).isInstanceOf(ErrorState::class.java)

    }

}