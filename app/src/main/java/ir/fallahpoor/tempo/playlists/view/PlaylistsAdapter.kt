package ir.fallahpoor.tempo.playlists.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ir.fallahpoor.tempo.R
import ir.fallahpoor.tempo.playlists.model.Playlist
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.list_item_playlist.view.*

class PlaylistsAdapter() : RecyclerView.Adapter<PlaylistsAdapter.PlaylistViewHolder>() {

    constructor(
        context: Context,
        playlists: List<Playlist>,
        clickListener: ((Playlist) -> Unit)?
    ) : this() {
        this.context = context
        this.playlists.addAll(playlists)
        this.clickListener = clickListener
    }

    private var context: Context? = null
    private val playlists = ArrayList<Playlist>()
    private var clickListener: ((Playlist) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return PlaylistViewHolder(inflater.inflate(R.layout.list_item_playlist, parent, false))
    }

    override fun getItemCount(): Int = playlists.size

    override fun onBindViewHolder(holder: PlaylistViewHolder, position: Int) {
        val playlist: Playlist = playlists[position]
        holder.bindData(playlist)
        holder.itemView.setOnClickListener { clickListener?.invoke(playlist) }
    }

    fun addPlaylists(playlists: List<Playlist>) {
        this.playlists.addAll(playlists)
        notifyItemRangeChanged(itemCount, playlists.size)
    }

    fun getPlaylists() = playlists

    inner class PlaylistViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        LayoutContainer {

        override val containerView: View?
            get() = itemView

        fun bindData(playlist: Playlist) {
            if (context != null) {
                Glide.with(context!!)
                    .load(playlist.images[0].url)
                    .placeholder(R.drawable.placeholder_category)
                    .into(itemView.playlistImageView)
            }
        }

    }

}