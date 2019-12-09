package ir.fallahpoor.tempo.common

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri

object Spotify {

    private const val SPOTIFY_PACKAGE_NAME = "com.spotify.music"

    fun isSpotifyInstalled(context: Context): Boolean {
        val pm: PackageManager = context.packageManager ?: return false
        return try {
            pm.getPackageInfo(SPOTIFY_PACKAGE_NAME, 0)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }

    fun openArtistPage(context: Context, uri: String?) {
        uri?.let {
            if (isSpotifyInstalled(context)) {
                openSpotify(context, it)
            }
        }
    }

    fun openSpotifyPageInPlayStore(context: Context) {

        val referrer =
            "adjust_campaign=PACKAGE_NAME&adjust_tracker=ndjczk&utm_source=adjust_preinstall"

        try {
            val uri = Uri.parse("market://details")
                .buildUpon()
                .appendQueryParameter("id", SPOTIFY_PACKAGE_NAME)
                .appendQueryParameter("referrer", referrer)
                .build()
            context.startActivity(Intent(Intent.ACTION_VIEW, uri))
        } catch (ex: ActivityNotFoundException) {
            val uri = Uri.parse("https://play.google.com/store/apps/details")
                .buildUpon()
                .appendQueryParameter("id", SPOTIFY_PACKAGE_NAME)
                .appendQueryParameter("referrer", referrer)
                .build()
            context.startActivity(Intent(Intent.ACTION_VIEW, uri))
        }

    }

    private fun openSpotify(context: Context, uri: String?) {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            putExtra(
                Intent.EXTRA_REFERRER,
                Uri.parse("android-app://" + context.packageName)
            )
            data = Uri.parse(uri)
        }
        context.startActivity(intent)
    }

}