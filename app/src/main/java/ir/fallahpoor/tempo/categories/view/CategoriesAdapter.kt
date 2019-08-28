package ir.fallahpoor.tempo.categories.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ir.fallahpoor.tempo.R
import ir.fallahpoor.tempo.categories.model.Category
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.list_item_category.view.*

class CategoriesAdapter() : RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder>() {

    constructor(
        context: Context,
        categories: List<Category>,
        clickListener: ((Category, ImageView, TextView) -> Unit)?
    ) : this() {
        this.context = context
        this.categories.addAll(categories)
        this.clickListener = clickListener
    }

    private var context: Context? = null
    private val categories = ArrayList<Category>()
    private var clickListener: ((Category, ImageView, TextView) -> Unit)? =
        null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return CategoryViewHolder(inflater.inflate(R.layout.list_item_category, parent, false))
    }

    override fun getItemCount(): Int = categories.size

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category: Category = categories[position]
        holder.bindData(category)
        holder.itemView.setOnClickListener {
            clickListener?.invoke(
                category,
                holder.itemView.categoryImageView,
                holder.itemView.categoryNameTextView
            )
        }
    }

    fun addCategories(categories: List<Category>) {
        this.categories.addAll(categories)
        notifyItemRangeChanged(itemCount, categories.size)
    }

    fun getCategories() = categories

    inner class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        LayoutContainer {

        override val containerView: View?
            get() = itemView

        fun bindData(category: Category) {
            itemView.categoryNameTextView.text = category.name
            itemView.categoryNameTextView.transitionName = category.id + "-TV"
            itemView.categoryImageView.transitionName = category.id + "-IV"
            if (context != null) {
                Glide.with(context!!)
                    .load(category.icons[0].url)
                    .placeholder(R.drawable.placeholder_category)
                    .into(itemView.categoryImageView)
            }
        }

    }

}