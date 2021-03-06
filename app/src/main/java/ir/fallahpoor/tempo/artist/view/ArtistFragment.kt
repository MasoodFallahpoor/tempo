package ir.fallahpoor.tempo.artist.view

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.TransitionInflater
import coil.api.load
import com.github.rongi.klaster.Klaster
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import ir.fallahpoor.tempo.R
import ir.fallahpoor.tempo.artist.viewmodel.ArtistViewModel
import ir.fallahpoor.tempo.common.*
import ir.fallahpoor.tempo.common.itemdecoration.SpaceItemDecoration
import ir.fallahpoor.tempo.data.entity.album.AlbumEntity
import ir.fallahpoor.tempo.data.entity.artist.ArtistAllInfoEntity
import ir.fallahpoor.tempo.data.entity.artist.ArtistEntity
import ir.fallahpoor.tempo.data.entity.track.TrackEntity
import kotlinx.android.synthetic.main.fragment_artist.*
import kotlinx.android.synthetic.main.list_item_album.view.*
import kotlinx.android.synthetic.main.list_item_search_result_artist.view.*
import kotlinx.android.synthetic.main.list_item_track.view.*

@AndroidEntryPoint
class ArtistFragment : Fragment() {

    private var artistId: String? = null
    private var artistName: String? = null
    private var artistImageUrl: String? = null
    private var artistUri: String? = null
    private val artistViewModel: ArtistViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        val transition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        sharedElementEnterTransition = transition
        sharedElementReturnTransition = transition

        getArgumentsFromBundle(arguments)
        setHasOptionsMenu(true)

    }

    private fun getArgumentsFromBundle(arguments: Bundle?) {
        arguments?.let {
            with(ArtistFragmentArgs) {
                artistId = fromBundle(it).artistId
                artistName = fromBundle(it).artistName
                artistImageUrl = fromBundle(it).artistImageUrl
                artistUri = fromBundle(it).artistUri
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_artist, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        artistNameTextView.text = artistName
        artistNameTextView.transitionName = "$artistId-TV"
        artistImageView.transitionName = "$artistId-IV"
        artistImageView.load(artistImageUrl!!)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        observeViewModel()
        artistViewModel.getArtist(artistId ?: "")
    }

    override fun onCreateOptionsMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_artist_fragment, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when (item.itemId) {
            R.id.openInSpotify -> {
                if (Spotify.isSpotifyInstalled(requireContext())) {
                    Spotify.openArtistPage(requireContext(), artistUri)
                } else {
                    showInstallSpotifySnackbar()
                }
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
                false
            }
        }

    private fun showInstallSpotifySnackbar() {
        Snackbar.make(artistImageView, R.string.spotify_not_installed, Snackbar.LENGTH_LONG)
            .setAction(getString(R.string.install_spotify)) {
                Spotify.openSpotifyPageInPlayStore(requireContext())
            }
            .show()
    }

    private fun observeViewModel() {
        artistViewModel.getViewState().observe(
            viewLifecycleOwner,
            Observer { viewState ->
                when (viewState) {
                    is LoadingState -> showLoading()
                    is DataLoadedState -> {
                        hideLoading()
                        renderArtistInformation(viewState.data)
                    }
                    is ErrorState -> {
                        hideLoading()
                        renderError(viewState.errorMessage)
                    }
                }
            })
    }

    private fun renderArtistInformation(artistAllInfoEntity: ArtistAllInfoEntity) {
        renderBaseInformation(artistAllInfoEntity.artist)
        renderTopTracks(artistAllInfoEntity.topTracks)
        renderAlbums(artistAllInfoEntity.albums)
        renderRelatedArtists(artistAllInfoEntity.relatedArtists)
        contentContainerLayout.fadeIn()
    }

    private fun renderBaseInformation(artistEntity: ArtistEntity) {
        noFollowersTextView.text = String.format("%,d", artistEntity.followers.total)
        renderGenres(artistEntity.genres)
    }

    private fun renderGenres(genres: List<String>) {
        artistGenresTextView.text =
            if (genres.isEmpty()) {
                getString(R.string.not_available)
            } else {
                genres.joinToString(separator = "\n") {
                    it.capitalize()
                }
            }
    }

    private fun renderTopTracks(topTracks: List<TrackEntity>) {
        with(artistTopTracksRecyclerView) {
            layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.VERTICAL,
                false
            )
            setHasFixedSize(true)
            adapter = getTopTracksAdapter(topTracks)
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )
        }
    }

    private fun getTopTracksAdapter(topTracks: List<TrackEntity>) = Klaster.get()
        .itemCount { topTracks.size }
        .view(R.layout.list_item_track, layoutInflater)
        .bind { position ->
            val track = topTracks[position]
            itemView.trackNameTextView.text = track.name
            itemView.trackAlbumNameTextView.text = track.album.name
        }
        .build()

    private fun renderAlbums(albums: List<AlbumEntity>) {
        if (albums.isNullOrEmpty()) {
            noArtistAlbumsTextView.visibility = View.VISIBLE
            artistAlbumsRecyclerView.visibility = View.GONE
        } else {
            with(artistAlbumsRecyclerView) {
                layoutManager = LinearLayoutManager(
                    context,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
                setHasFixedSize(true)
                adapter = getAlbumsAdapter(albums)
                addItemDecoration(
                    SpaceItemDecoration(
                        context,
                        8f,
                        SpaceItemDecoration.Orientation.HORIZONTAL
                    )
                )
                visibility = View.VISIBLE
            }
            noArtistAlbumsTextView.visibility = View.GONE
        }
    }

    private fun getAlbumsAdapter(albums: List<AlbumEntity>) = Klaster.get()
        .itemCount { albums.size }
        .view(R.layout.list_item_album, layoutInflater)
        .bind { position ->
            val album = albums[position]
            itemView.albumNameTextView.text = album.name
            if (album.images.isNotEmpty()) {
                itemView.albumCoverImageView.load(album.images[0].url)
            }
        }
        .build()

    private fun renderRelatedArtists(relatedArtists: List<ArtistEntity>) {
        if (relatedArtists.isNullOrEmpty()) {
            noRelatedArtistsTextView.visibility = View.VISIBLE
            artistRelatedArtistsRecyclerView.visibility = View.GONE
        } else {
            with(artistRelatedArtistsRecyclerView) {
                layoutManager = LinearLayoutManager(
                    context,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
                setHasFixedSize(true)
                adapter = getRelatedArtistsAdapter(relatedArtists)
                addItemDecoration(
                    SpaceItemDecoration(
                        context,
                        8f,
                        SpaceItemDecoration.Orientation.HORIZONTAL
                    )
                )
                visibility = View.VISIBLE
            }
            noRelatedArtistsTextView.visibility = View.GONE
        }
    }

    private fun getRelatedArtistsAdapter(relatedArtists: List<ArtistEntity>) = Klaster.get()
        .itemCount { relatedArtists.size }
        .view(R.layout.list_item_search_result_artist, layoutInflater)
        .bind { position ->
            val artist = relatedArtists[position]
            itemView.artistNameTextView.text = artist.name
            itemView.artistNameTextView.transitionName = artist.id + "-TV"
            itemView.artistImageView.transitionName = artist.id + "-IV"
            if (artist.images.isNotEmpty()) {
                itemView.artistImageView.load(artist.images[0].url)
            }
            itemView.setOnClickListener {
                val action = ArtistFragmentDirections.actionArtistFragmentSelf(
                    artist.id,
                    artist.name,
                    artist.images[0].url,
                    artist.uri
                )
                val extras = FragmentNavigatorExtras(
                    itemView.artistImageView to itemView.artistImageView.transitionName,
                    itemView.artistNameTextView to itemView.artistNameTextView.transitionName
                )
                findNavController().navigate(action, extras)
            }
        }
        .build()

    private fun showLoading() {
        progressBar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        progressBar.visibility = View.GONE
    }

    private fun renderError(message: String) {
        Snackbar.make(artistImageView, message, Snackbar.LENGTH_INDEFINITE)
            .setAction(R.string.try_again) {
                artistViewModel.getArtist(artistId ?: "")
            }
            .show()
    }

}
