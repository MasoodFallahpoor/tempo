package ir.fallahpoor.tempo.data.common

import android.content.Context
import android.content.SharedPreferences
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner


@RunWith(RobolectricTestRunner::class)
class PreferencesManagerTest {

    private companion object {
        const val ACCESS_TOKEN = "A65eR0pt99bNmq12"
        const val KEY_ACCESS_TOKEN = "access_token"
    }

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var preferencesManager: PreferencesManager
    private val context: Context = ApplicationProvider.getApplicationContext()

    @Before
    fun runBeforeEachTest() {
        sharedPreferences = context.getSharedPreferences("test_preferences", Context.MODE_PRIVATE)
        preferencesManager = PreferencesManager(sharedPreferences)
    }

    @Test
    fun getAccessToken_should_return_access_token() {

        // Given
        sharedPreferences.edit()
            .putString(
                KEY_ACCESS_TOKEN,
                ACCESS_TOKEN
            )
            .commit()

        // When
        val accessToken: String? = preferencesManager.getAccessToken()

        // Then
        Truth.assertThat(accessToken).isEqualTo(ACCESS_TOKEN)

    }

    @Test
    fun getAccessToken_should_return_null() {

        // Given that by default 'access token' is null

        // When
        val accessToken: String? = preferencesManager.getAccessToken()

        // Then
        Truth.assertThat(accessToken).isNull()

    }

    @Test
    fun test_setAccessToken() {

        // Given

        // When
        preferencesManager.setAccessToken(ACCESS_TOKEN)

        // Then
        Truth.assertThat(preferencesManager.getAccessToken()).isEqualTo(ACCESS_TOKEN)

    }

}