//package ir.fallahpoor.tempo.data.repository.authentication
//
//import androidx.arch.core.executor.testing.InstantTaskExecutorRule
//import androidx.lifecycle.LiveData
//import com.google.common.truth.Truth
//import ir.fallahpoor.tempo.data.common.Error
//import ir.fallahpoor.tempo.data.common.PreferencesManager
//import ir.fallahpoor.tempo.data.common.Resource
//import ir.fallahpoor.tempo.data.entity.AccessTokenEntity
//import ir.fallahpoor.tempo.data.webservice.AccessTokenWebService
//import org.junit.Before
//import org.junit.Rule
//import org.junit.Test
//import org.junit.rules.TestRule
//import org.junit.runner.RunWith
//import org.mockito.ArgumentMatchers.anyString
//import org.mockito.Mock
//import org.mockito.Mockito
//import org.mockito.junit.MockitoJUnitRunner
//import retrofit2.Response
//import retrofit2.mock.Calls
//
//@RunWith(MockitoJUnitRunner::class)
//class AuthenticationRepositoryImplTest {
//
//    private companion object {
//        const val ACCESS_TOKEN = "some_access_token"
//        const val ERROR_MESSAGE = "some_error_message"
//        const val GRANT_TYPE = "client_credentials"
//    }
//
//    @Rule
//    @JvmField
//    var rule: TestRule = InstantTaskExecutorRule()
//    private lateinit var authenticationRepositoryImpl: AuthenticationRepositoryImpl
//    @Mock
//    private lateinit var accessTokenWebService: AccessTokenWebService
//    @Mock
//    private lateinit var preferencesManager: PreferencesManager
//
//    @Before
//    fun runBeforeEachTest() {
//        authenticationRepositoryImpl =
//            AuthenticationRepositoryImpl(accessTokenWebService, preferencesManager)
//    }
//
//    @Test
//    fun getAccessToken_should_not_interact_with_AccessTokenWebService() {
//
//        // Given
//        Mockito.`when`(preferencesManager.getAccessToken()).thenReturn(ACCESS_TOKEN)
//
//        // When
//        val actualLiveData: LiveData<Resource<Unit>> = authenticationRepositoryImpl.getAccessToken()
//        actualLiveData.observeForever {
//        }
//
//        // Then
//        Mockito.verifyZeroInteractions(accessTokenWebService)
//        Truth.assertThat(actualLiveData.value?.status).isEqualTo(Resource.Status.SUCCESS)
//        Truth.assertThat(actualLiveData.value?.data).isEqualTo(null)
//        Truth.assertThat(actualLiveData.value?.error).isEqualTo(null)
//
//    }
//
//    @Test
//    fun getAccessToken_should_get_access_token() {
//
//        // Given
//        Mockito.`when`(preferencesManager.getAccessToken()).thenReturn(null)
//        val accessTokenEntity = AccessTokenEntity(
//            ACCESS_TOKEN,
//            "token_type",
//            3600,
//            "scope"
//        )
//        Mockito.`when`(accessTokenWebService.getAccessToken(GRANT_TYPE))
//            .thenReturn(Calls.response(Response.success(accessTokenEntity)))
//
//
//        // When
//        val actualLiveData: LiveData<Resource<Unit>> = authenticationRepositoryImpl.getAccessToken()
//        actualLiveData.observeForever {
//        }
//
//        // Then
//        Mockito.verify(accessTokenWebService).getAccessToken(GRANT_TYPE)
//        Mockito.verify(preferencesManager).setAccessToken(ACCESS_TOKEN)
//        Truth.assertThat(actualLiveData.value?.status).isEqualTo(Resource.Status.SUCCESS)
//        Truth.assertThat(actualLiveData.value?.data).isEqualTo(null)
//        Truth.assertThat(actualLiveData.value?.error).isEqualTo(null)
//
//    }
//
//    @Test
//    fun getAccessToken_should_fail() {
//
//        // Given
//        Mockito.`when`(preferencesManager.getAccessToken()).thenReturn(null)
//        Mockito.`when`(accessTokenWebService.getAccessToken(GRANT_TYPE))
//            .thenReturn(Calls.failure(Throwable(ERROR_MESSAGE)))
//
//        // When
//        val actualLiveData: LiveData<Resource<Unit>> = authenticationRepositoryImpl.getAccessToken()
//        actualLiveData.observeForever {
//        }
//
//        // Then
//        Mockito.verify(accessTokenWebService).getAccessToken(GRANT_TYPE)
//        Mockito.verify(preferencesManager, Mockito.never()).setAccessToken(anyString())
//        Truth.assertThat(actualLiveData.value?.status).isEqualTo(Resource.Status.ERROR)
//        Truth.assertThat(actualLiveData.value?.data).isEqualTo(null)
//        Truth.assertThat(actualLiveData.value?.error).isEqualTo(
//            Error(
//                ERROR_MESSAGE
//            )
//        )
//
//    }
//
//}