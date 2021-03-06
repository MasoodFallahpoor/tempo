package ir.fallahpoor.tempo.data.common

import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferencesManager @Inject constructor(private val sharedPreferences: SharedPreferences) {

    private companion object {
        const val KEY_ACCESS_TOKEN = "access_token"
    }

    fun getAccessToken() : String? =
        sharedPreferences.getString(KEY_ACCESS_TOKEN, null)

    fun setAccessToken(accessToken: String?) =
        sharedPreferences.edit()
            .putString(KEY_ACCESS_TOKEN, accessToken)
            .commit()

}