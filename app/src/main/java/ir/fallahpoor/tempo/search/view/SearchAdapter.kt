package ir.fallahpoor.tempo.search.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import ir.fallahpoor.tempo.R
import ir.fallahpoor.tempo.data.entity.album.AlbumEntity
import ir.fallahpoor.tempo.data.entity.artist.ArtistEntity
import ir.fallahpoor.tempo.data.entity.common.ListEntity
import ir.fallahpoor.tempo.data.entity.playlist.PlaylistEntity
import ir.fallahpoor.tempo.data.entity.track.TrackEntity
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.list_item_search_result_album.view.*
import kotlinx.android.synthetic.main.list_item_search_result_artist.view.*
import kotlinx.android.synthetic.main.list_item_search_result_playlist.view.*
import kotlinx.android.synthetic.main.list_item_search_result_track.view.*

class SearchAdapter<T>(
    private val data: ListEntity<T>,
    private val type: Type,
    private val clickListener: ((T, ImageView, TextView) -> Unit)?
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    enum class Type {
        ALBUM,
        ARTIST,
        TRACK,
        PLAYLIST
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val inflater = LayoutInflater.from(parent.context)

        return when (type) {
            Type.ALBUM -> {
                AlbumViewHolder(
                    inflater.inflate(
                        R.layout.list_item_search_result_album,
                        parent,
                        false
                    )
                )
            }
            Type.ARTIST -> {
                ArtistViewHolder(
                    inflater.inflate(
                        R.layout.list_item_search_result_artist,
                        parent,
                        false
                    )
                )
            }
            Type.TRACK -> {
                TrackViewHolder(
                    inflater.inflate(
                        R.layout.list_item_search_result_track,
                        parent,
                        false
                    )
                )
            }
            Type.PLAYLIST -> {
                PlaylistViewHolder(
                    inflater.inflate(
                        R.layout.list_item_search_result_playlist,
                        parent,
                        false
                    )
                )
            }
        }

    }

    override fun getItemCount(): Int = data.items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (holder) {
            is SearchAdapter<*>.AlbumViewHolder -> holder.bindData(data.items[position])
            is SearchAdapter<*>.ArtistViewHolder -> {
                holder.bindData(data.items[position])
                holder.itemView.setOnClickListener {
                    clickListener?.invoke(
                        data.items[position],
                        holder.itemView.artistImageView,
                        holder.itemView.artistNameTextView
                    )
                }
            }
            is SearchAdapter<*>.TrackViewHolder -> holder.bindData(data.items[position])
            is SearchAdapter<*>.PlaylistViewHolder -> holder.bindData(data.items[position])
        }

    }

    inner class AlbumViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        LayoutContainer {

        override val containerView: View?
            get() = itemView

        fun <T> bindData(t: T) {

            val album = t as AlbumEntity

            itemView.albumNameTextView.text = album.name
            itemView.albumArtistNameTextView.text = album.artists[0].name

            if (album.images.isNotEmpty()) {
                itemView.albumCoverImageView.load(album.images[0].url) {
                    placeholder(R.drawable.placeholder)
                }
            }

        }

    }

    inner class ArtistViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        LayoutContainer {

        override val containerView: View?
            get() = itemView

        fun <T> bindData(t: T) {

            val artist = t as ArtistEntity

            itemView.artistNameTextView.text = artist.name
            itemView.artistNameTextView.transitionName = artist.id + "-TV"

            itemView.artistImageView.transitionName = artist.id + "-IV"

            if (artist.images.isNotEmpty()) {
                itemView.artistImageView.load(artist.images[0].url)
            }

        }

    }

    inner class TrackViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        LayoutContainer {

        override val containerView: View?
            get() = itemView

        fun <T> bindData(t: T) {

            val track = t as TrackEntity

            itemView.trackNameTextView.text = track.name
            itemView.trackAlbumNameTextView.text = track.album.name
            itemView.trackArtistNameTextView.text = track.artists[0].name

            if (track.album.images.isNotEmpty()) {
                itemView.trackCoverImageView.load(track.album.images[0].url)
            }

        }

    }

    inner class PlaylistViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        LayoutContainer {

        override val containerView: View?
            get() = itemView

        fun <T> bindData(t: T) {

            val playlist = t as PlaylistEntity

            itemView.playlistNameTextView.text = playlist.name

            if (playlist.images.isNotEmpty()) {
                itemView.playlistCoverImageView.load(playlist.images[0].url)
            }

        }

    }

}