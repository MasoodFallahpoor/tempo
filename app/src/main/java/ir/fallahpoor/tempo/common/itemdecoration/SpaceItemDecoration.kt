package ir.fallahpoor.tempo.common.itemdecoration

import android.content.Context
import android.graphics.Rect
import android.util.TypedValue
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class SpaceItemDecoration() : RecyclerView.ItemDecoration() {

    enum class Orientation {
        HORIZONTAL,
        VERTICAL
    }

    private enum class LayoutManagerType {
        LINEAR,
        GRID
    }

    private lateinit var layoutManagerType: LayoutManagerType
    private var orientation: Orientation = Orientation.VERTICAL
    private var spaceInPx: Int = 0
    private var spanCount: Int = 2

    constructor(context: Context, spaceInDp: Float, orientation: Orientation) : this() {
        spaceInPx = getSpaceInPx(context, spaceInDp)
        this.orientation = orientation
        layoutManagerType = LayoutManagerType.LINEAR
    }

    constructor(context: Context, spaceInDp: Float, spanCount: Int) : this() {
        spaceInPx = getSpaceInPx(context, spaceInDp)
        this.spanCount = spanCount
        layoutManagerType = LayoutManagerType.GRID
    }

    private fun getSpaceInPx(context: Context, spaceInDp: Float): Int =
        TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            spaceInDp,
            context.resources.displayMetrics
        ).toInt()

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {

        if (layoutManagerType == LayoutManagerType.LINEAR) {
            setSpacesForLinearLayout(parent, view, outRect)
        } else {
            setSpacesForGridLayout(parent, view, outRect)
        }

    }

    private fun setSpacesForLinearLayout(
        parent: RecyclerView,
        view: View,
        outRect: Rect
    ) {

        val itemPosition = parent.getChildAdapterPosition(view)

        with(outRect) {
            if (orientation == Orientation.VERTICAL) {
                if (itemPosition == 0) {
                    top = spaceInPx
                }
                left = spaceInPx
            } else {
                if (itemPosition == 0) {
                    left = spaceInPx
                }
                top = spaceInPx
            }
            right = spaceInPx
            bottom = spaceInPx
        }

    }

    private fun setSpacesForGridLayout(
        parent: RecyclerView,
        view: View,
        outRect: Rect
    ) {

        val itemPosition = parent.getChildAdapterPosition(view)
        val itemColumn = itemPosition % spanCount

        with(outRect) {
            if (itemPosition < spanCount) {
                top = spaceInPx
            }
            left = spaceInPx - (itemColumn * spaceInPx) / spanCount
            right = ((itemColumn + 1) * spaceInPx) / spanCount
            bottom = spaceInPx
        }

    }

}