package ir.fallahpoor.tempo.playlists.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ir.fallahpoor.tempo.R
import ir.fallahpoor.tempo.common.extensions.load
import ir.fallahpoor.tempo.data.entity.playlist.PlaylistEntity
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.list_item_playlist.view.*

class PlaylistsAdapter(
    private val context: Context,
    private val clickListener: ((PlaylistEntity) -> Unit)?
) :
    PagedListAdapter<PlaylistEntity, PlaylistsAdapter.PlaylistViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return PlaylistViewHolder(inflater.inflate(R.layout.list_item_playlist, parent, false))
    }

    override fun onBindViewHolder(holder: PlaylistViewHolder, position: Int) {
        holder.bindData(getItem(position))
    }

    inner class PlaylistViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        LayoutContainer {

        override val containerView: View?
            get() = itemView

        fun bindData(playlist: PlaylistEntity?) {

            if (playlist == null) {
                itemView.shimmerLayout.visibility = View.VISIBLE
                itemView.playlistImageView.setImageResource(0)
                itemView.setOnClickListener {}
            } else {
                itemView.shimmerLayout.visibility = View.GONE
                itemView.shimmerLayout.stopShimmer()
                itemView.playlistImageView.load(playlist.images[0].url)
            }
        }

    }

    companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<PlaylistEntity> =
            object : DiffUtil.ItemCallback<PlaylistEntity>() {
                override fun areItemsTheSame(@NonNull oldItem: PlaylistEntity, @NonNull newItem: PlaylistEntity) =
                    oldItem.id == newItem.id

                override fun areContentsTheSame(@NonNull oldItem: PlaylistEntity, @NonNull newItem: PlaylistEntity) =
                    oldItem == newItem
            }
    }

}