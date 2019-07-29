package ir.fallahpoor.tempo.browse.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ir.fallahpoor.tempo.R
import ir.fallahpoor.tempo.browse.model.Category
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.list_item_category.view.*

class CategoriesAdapter() : RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder>() {

    constructor(
        context: Context,
        categories: List<Category>,
        clickListener: ((Category) -> Unit)?
    ) : this() {
        this.context = context
        this.categories.addAll(categories)
        this.clickListener = clickListener
    }

    private var context: Context? = null
    private val categories = ArrayList<Category>()
    private var clickListener: ((Category) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CategoryViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_category, parent, false)
        )

    override fun getItemCount(): Int = categories.size

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category: Category = categories[position]
        holder.bindData(category)
        holder.itemView.setOnClickListener { clickListener?.invoke(category) }
    }

    fun addCategories(news: List<Category>) {
        categories.addAll(news)
        notifyItemRangeChanged(itemCount, news.size)
    }

    fun getCategories() = categories

    inner class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        LayoutContainer {

        override val containerView: View?
            get() = itemView

        fun bindData(category: Category) {
            itemView.categoryNameTextView.text = category.name
            if (context != null) {
                Glide.with(context!!)
                    .load(category.icons[0].url)
                    .placeholder(R.drawable.placeholder_category)
                    .into(itemView.categoryImageView)
            }
        }

    }

}