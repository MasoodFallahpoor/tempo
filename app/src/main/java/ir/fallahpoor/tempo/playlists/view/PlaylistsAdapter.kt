package ir.fallahpoor.tempo.playlists.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ir.fallahpoor.tempo.R
import ir.fallahpoor.tempo.common.extensions.load
import ir.fallahpoor.tempo.data.entity.playlist.PlaylistEntity
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.list_item_playlist.view.*

class PlaylistsAdapter : RecyclerView.Adapter<PlaylistsAdapter.PlaylistViewHolder>() {

    private val playlists = mutableListOf<PlaylistEntity>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return PlaylistViewHolder(inflater.inflate(R.layout.list_item_playlist, parent, false))
    }

    override fun onBindViewHolder(holder: PlaylistViewHolder, position: Int) {
        holder.bindData(playlists[position])
    }

    override fun getItemCount(): Int = playlists.size

    fun addPlaylists(playlists: List<PlaylistEntity>) {
        this.playlists.addAll(playlists)
        notifyItemRangeInserted(itemCount, playlists.size)
    }

    inner class PlaylistViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        LayoutContainer {

        override val containerView: View?
            get() = itemView

        fun bindData(playlist: PlaylistEntity) {
            itemView.playlistImageView.load(playlist.images[0].url)
        }

    }

}