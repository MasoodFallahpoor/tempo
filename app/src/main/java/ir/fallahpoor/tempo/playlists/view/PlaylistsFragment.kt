package ir.fallahpoor.tempo.playlists.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.transition.TransitionInflater
import com.google.android.material.snackbar.Snackbar
import ir.fallahpoor.tempo.R
import ir.fallahpoor.tempo.app.TempoApplication
import ir.fallahpoor.tempo.common.EndlessScrollListener
import ir.fallahpoor.tempo.common.extensions.load
import ir.fallahpoor.tempo.common.viewstate.*
import ir.fallahpoor.tempo.playlists.model.Playlist
import ir.fallahpoor.tempo.playlists.viewmodel.PlaylistsViewModel
import ir.fallahpoor.tempo.playlists.viewmodel.PlaylistsViewModelFactory
import kotlinx.android.synthetic.main.fragment_playlists.*
import javax.inject.Inject

class PlaylistsFragment : Fragment() {

    private var categoryId: String? = null
    private var categoryName: String? = null
    private var categoryIconUrl: String? = null

    @Inject
    lateinit var playlistsViewModelFactory: PlaylistsViewModelFactory
    private lateinit var playlistsViewModel: PlaylistsViewModel
    private var playlistsAdapter = PlaylistsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        postponeEnterTransition()

        val transition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        sharedElementEnterTransition = transition
        sharedElementReturnTransition = transition

        categoryId = arguments?.let { PlaylistsFragmentArgs.fromBundle(it).categoryId }
        categoryName = arguments?.let { PlaylistsFragmentArgs.fromBundle(it).categoryName }
        categoryIconUrl = arguments?.let { PlaylistsFragmentArgs.fromBundle(it).categoryIconUrl }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_playlists, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        categoryIconImageView.transitionName = "$categoryId-IV"
        categoryNameTextView.text = categoryName
        categoryNameTextView.transitionName = "$categoryId-TV"
        categoryIconImageView.load(categoryIconUrl!!) {
            startPostponedEnterTransition()
        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        injectViewModelFactory()
        setupRecyclerView()
        setupViewModel()
        subscribeToViewModel()
        playlistsViewModel.getPlaylists(categoryId!!)
    }

    private fun injectViewModelFactory() {
        (activity?.application as TempoApplication).appComponent.inject(this)
    }

    private fun setupRecyclerView() {
        with(playlistsRecyclerView) {
            layoutManager = GridLayoutManager(context, 2)
            adapter = playlistsAdapter
            addOnScrollListener(object : EndlessScrollListener() {
                override fun onLoadMore() {
                    playlistsViewModel.getMorePlaylists()
                }
            })
        }
    }

    private fun setupViewModel() {
        playlistsViewModel = ViewModelProviders.of(this, playlistsViewModelFactory)
            .get(PlaylistsViewModel::class.java)
    }

    private fun subscribeToViewModel() {
        playlistsViewModel.getViewStateLiveData().observe(viewLifecycleOwner,
            Observer { viewState ->
                hideLoading()
                when (viewState) {
                    is LoadingViewState -> showLoading()
                    is DataErrorViewState -> renderError(viewState.getMessage())
                    is MoreDataErrorViewState -> renderMoreDataError(viewState.getMessage())
                    is MoreDataLoadedViewState<*> -> renderMorePlaylists(viewState.data as List<Playlist>)
                    is DataLoadedViewState<*> -> renderPlaylists(viewState.data as List<Playlist>)
                }
            })
    }

    private fun renderMorePlaylists(playlists: List<Playlist>) {
        playlistsAdapter.addPlaylists(playlists.minus(playlistsAdapter.getPlaylists()))
    }

    private fun showLoading() {
        progressBar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        progressBar.visibility = View.GONE
    }

    private fun renderPlaylists(playlists: List<Playlist>) {
        if (playlists.isEmpty()) {
            playlistsRecyclerView.visibility = View.GONE
            noPlaylistTextView.visibility = View.VISIBLE
        } else {
            playlistsAdapter = createPlaylistsAdapter(playlists)
            playlistsRecyclerView.adapter = playlistsAdapter
        }
    }

    private fun createPlaylistsAdapter(playlists: List<Playlist>) =
        PlaylistsAdapter(context!!, playlists, null)

    private fun renderError(message: String) {
        Snackbar.make(playlistsRecyclerView, message, Snackbar.LENGTH_LONG)
            .setAction(R.string.try_again) {
                playlistsViewModel.getPlaylists(categoryId!!)
            }
            .show()
    }

    private fun renderMoreDataError(message: String) {
        Snackbar.make(playlistsRecyclerView, message, Snackbar.LENGTH_LONG)
            .setAction(R.string.try_again) {
                playlistsViewModel.getMorePlaylists()
            }
            .show()
    }

}

