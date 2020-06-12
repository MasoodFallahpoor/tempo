package ir.fallahpoor.tempo.categories.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import ir.fallahpoor.tempo.R
import ir.fallahpoor.tempo.data.entity.category.CategoryEntity
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.list_item_category.view.*

class CategoriesAdapter(
    private val clickListener: (CategoryEntity, ImageView, TextView) -> Unit
) : RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder>() {

    private val categories = mutableListOf<CategoryEntity>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(R.layout.list_item_category, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bindData(categories[position])
    }

    override fun getItemCount(): Int = categories.size

    fun addCategories(categories: List<CategoryEntity>) {
        this.categories.addAll(categories)
        notifyItemRangeInserted(itemCount, categories.size)
    }

    inner class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        LayoutContainer {

        override val containerView: View?
            get() = itemView

        fun bindData(category: CategoryEntity) {
            with(itemView) {
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
                categoryImageView.load(category.icons[0].url) {
                    placeholder(R.drawable.placeholder)
                    crossfade(true)
                }
            }
        }

    }

}