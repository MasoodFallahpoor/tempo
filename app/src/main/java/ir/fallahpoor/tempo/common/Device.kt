package ir.fallahpoor.tempo.common

import android.content.Context

object Device {

    fun getScreenWidthInDp(context: Context): Float {
        val displayMetrics = context.resources.displayMetrics
        return (displayMetrics.widthPixels / displayMetrics.density)
    }

}