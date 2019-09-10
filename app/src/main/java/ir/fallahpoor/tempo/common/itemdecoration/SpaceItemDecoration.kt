package ir.fallahpoor.tempo.common.itemdecoration

import android.content.Context
import android.graphics.Rect
import android.util.TypedValue
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView

class SpaceItemDecoration(context: Context, spaceInDp: Float, orientation: Int) :
    RecyclerView.ItemDecoration() {

    companion object {
        const val HORIZONTAL = LinearLayout.HORIZONTAL
        const val VERTICAL = LinearLayout.VERTICAL
    }

    private var orientation: Int = VERTICAL
    private var spaceInPx: Int = 0

    init {
        setOrientation(orientation)
        spaceInPx = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            spaceInDp,
            context.resources.displayMetrics
        ).toInt()
    }

    private fun setOrientation(orientation: Int) {
        require(orientation == HORIZONTAL || orientation != VERTICAL) { "Invalid orientation. It should be either HORIZONTAL or VERTICAL" }
        this.orientation = orientation
    }

    override fun getItemOffsets(
        outRect: Rect, view: View,
        parent: RecyclerView, state: RecyclerView.State
    ) {

        with(outRect) {
            if (orientation == VERTICAL) {
                if (parent.getChildAdapterPosition(view) == 0) {
                    top = spaceInPx
                }
                left = spaceInPx
            } else {
                if (parent.getChildAdapterPosition(view) == 0) {
                    left = spaceInPx
                }
                top = spaceInPx
            }

            right = spaceInPx
            bottom = spaceInPx
        }

    }

}