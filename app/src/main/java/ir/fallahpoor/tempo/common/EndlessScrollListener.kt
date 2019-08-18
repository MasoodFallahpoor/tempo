package ir.fallahpoor.tempo.common

import androidx.annotation.NonNull
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class EndlessScrollListener : RecyclerView.OnScrollListener() {

    private companion object {
        const val THRESHOLD_ROWS = 4
    }

    private var previousTotalRowsCount: Int = 0
    private var isLoading = true

    override fun onScrolled(@NonNull recyclerView: RecyclerView, dx: Int, dy: Int) {

        super.onScrolled(recyclerView, dx, dy)

        val totalRowsCount = getTotalRowsCount(recyclerView.layoutManager)
        val lastVisibleRowPosition = getLastVisibleRowPosition(recyclerView.layoutManager)

        if (isLoading) {
            if (totalRowsCount > previousTotalRowsCount) {
                previousTotalRowsCount = totalRowsCount
                isLoading = false
            }
        }

        if (!isLoading && totalRowsCount - lastVisibleRowPosition <= THRESHOLD_ROWS) {
            isLoading = true
            onLoadMore()
        }

    }

    private fun getTotalRowsCount(layoutManager: RecyclerView.LayoutManager?): Int {

        return when (layoutManager) {
            is GridLayoutManager -> {
                val gridLayoutManager = layoutManager as GridLayoutManager?
                gridLayoutManager!!.itemCount / gridLayoutManager.spanCount
            }
            is LinearLayoutManager -> {
                val linearLayoutManager = layoutManager as LinearLayoutManager?
                linearLayoutManager!!.itemCount
            }
            else -> throw IllegalArgumentException("LayoutManager MUST either be LinearLayoutManager or GridLayoutManager")
        }

    }

    fun reset() {
        previousTotalRowsCount = 0
        isLoading = true
    }

    abstract fun onLoadMore()

    private fun getLastVisibleRowPosition(layoutManager: RecyclerView.LayoutManager?): Int {

        return when (layoutManager) {
            is GridLayoutManager -> {
                val gridLayoutManager = layoutManager as GridLayoutManager?
                gridLayoutManager!!.findLastVisibleItemPosition() / gridLayoutManager.spanCount
            }
            is LinearLayoutManager -> {
                val linearLayoutManager = layoutManager as LinearLayoutManager?
                linearLayoutManager!!.findLastVisibleItemPosition()
            }
            else -> throw IllegalArgumentException("LayoutManager MUST either be LinearLayoutManager or GridLayoutManager")
        }

    }

}