package ir.fallahpoor.tempo.common

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.view.View
import androidx.core.view.isVisible

private const val ANIM_DURATION_MS: Long = 500

fun View.fadeIn() {

    if (isVisible) {
        return
    }

    alpha = 0f
    visibility = View.VISIBLE
    animate()
        .alpha(1f)
        .setDuration(ANIM_DURATION_MS)
        .setListener(null)

}

fun View.fadeOut() {

    if (!isVisible) {
        return
    }

    animate()
        .alpha(0f)
        .setDuration(ANIM_DURATION_MS)
        .setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                visibility = View.GONE
            }
        })

}
