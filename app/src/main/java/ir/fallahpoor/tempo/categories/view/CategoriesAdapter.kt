package ir.fallahpoor.tempo.categories.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ir.fallahpoor.tempo.R
import ir.fallahpoor.tempo.common.extensions.load
import ir.fallahpoor.tempo.data.entity.category.CategoryEntity
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.list_item_category.view.*

class CategoriesAdapter(
    private val clickListener: (CategoryEntity, ImageView, TextView) -> Unit
) : PagedListAdapter<CategoryEntity, CategoriesAdapter.CategoryViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return CategoryViewHolder(inflater.inflate(R.layout.list_item_category, parent, false))
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bindData(getItem(position))
    }

    inner class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        LayoutContainer {

        override val containerView: View?
            get() = itemView

        fun bindData(category: CategoryEntity?) {
            with(itemView) {
                if (category == null) {
                    shimmerLayout.visibility = View.VISIBLE
                    categoryNameTextView.visibility = View.INVISIBLE
                    categoryImageView.setImageResource(0)
                    setOnClickListener {}
                } else {
                    shimmerLayout.visibility = View.GONE
                    shimmerLayout.stopShimmer()
                    categoryNameTextView.visibility = View.VISIBLE
                    categoryNameTextView.text = category.name
                    categoryNameTextView.transitionName = category.id + "-TV"
                    categoryImageView.transitionName = category.id + "-IV"
                    setOnClickListener {
                        clickListener(
                            category,
                            categoryImageView,
                            categoryNameTextView
                        )
                    }
                    categoryImageView.load(category.icons[0].url)
                }
            }
        }

    }

    companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<CategoryEntity> =
            object : DiffUtil.ItemCallback<CategoryEntity>() {
                override fun areItemsTheSame(@NonNull oldItem: CategoryEntity, @NonNull newItem: CategoryEntity) =
                    oldItem.id == newItem.id

                override fun areContentsTheSame(@NonNull oldItem: CategoryEntity, @NonNull newItem: CategoryEntity) =
                    oldItem == newItem
            }
    }

}