package ir.fallahpoor.tempo.categories.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ir.fallahpoor.tempo.R
import ir.fallahpoor.tempo.data.entity.category.CategoryEntity
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.list_item_category.view.*

class CategoriesAdapter(
    private val context: Context,
    private val clickListener: ((CategoryEntity, ImageView, TextView) -> Unit)
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

            if (category == null) {
                itemView.shimmerLayout.visibility = View.VISIBLE
                itemView.categoryNameTextView.visibility = View.INVISIBLE
                itemView.categoryImageView.setImageResource(0)
                itemView.setOnClickListener {}
            } else {
                itemView.shimmerLayout.visibility = View.GONE
                itemView.shimmerLayout.stopShimmer()
                itemView.categoryNameTextView.visibility = View.VISIBLE
                itemView.categoryNameTextView.text = category.name
                itemView.categoryNameTextView.transitionName = category.id + "-TV"
                itemView.categoryImageView.transitionName = category.id + "-IV"
                itemView.setOnClickListener {
                    clickListener(
                        category,
                        itemView.categoryImageView,
                        itemView.categoryNameTextView
                    )
                }
                Glide.with(context)
                    .load(category.icons[0].url)
                    .placeholder(R.drawable.placeholder_category)
                    .into(itemView.categoryImageView)
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