package ir.fallahpoor.tempo.playlists.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import androidx.recyclerview.widget.GridLayoutManager
import androidx.transition.TransitionInflater
import com.google.android.material.snackbar.Snackbar
import ir.fallahpoor.tempo.R
import ir.fallahpoor.tempo.app.TempoApplication
import ir.fallahpoor.tempo.common.ViewModelFactory
import ir.fallahpoor.tempo.common.extensions.load
import ir.fallahpoor.tempo.data.common.State
import ir.fallahpoor.tempo.data.entity.playlist.PlaylistEntity
import ir.fallahpoor.tempo.playlists.viewmodel.PlaylistsViewModel
import kotlinx.android.synthetic.main.fragment_playlists.*
import javax.inject.Inject

class PlaylistsFragment : Fragment() {

    private var categoryId: String? = null
    private var categoryName: String? = null
    private var categoryIconUrl: String? = null

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var playlistsViewModel: PlaylistsViewModel
    private lateinit var playlistsAdapter: PlaylistsAdapter

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
        setupAdapter()
        setupRecyclerView()
        setupViewModel()
        observeViewModel()
        playlistsViewModel.getPlaylists(categoryId ?: "")
    }

    private fun injectViewModelFactory() {
        (activity?.application as TempoApplication).appComponent.inject(this)
    }

    private fun setupAdapter() {
        playlistsAdapter = PlaylistsAdapter(context!!, null)
    }

    private fun setupRecyclerView() {
        with(playlistsRecyclerView) {
            layoutManager = GridLayoutManager(context, 2)
            adapter = playlistsAdapter
            setHasFixedSize(true)
        }
    }

    private fun setupViewModel() {
        playlistsViewModel = ViewModelProvider(this, viewModelFactory)
            .get(PlaylistsViewModel::class.java)
    }

    private fun observeViewModel() {

        playlistsViewModel.playlists.observe(
            viewLifecycleOwner,
            Observer { playlists: PagedList<PlaylistEntity> ->
                renderPlaylists(playlists)
            }
        )

        playlistsViewModel.state.observe(
            viewLifecycleOwner,
            Observer { state: State ->
                when (state.status) {
                    State.Status.LOADED -> hideLoading()
                    State.Status.LOADING -> showLoading()
                    State.Status.LOADING_MORE -> {
                    }
                    State.Status.ERROR, State.Status.ERROR_MORE -> {
                        hideLoading()
                        renderError(state.message)
                    }
                }
            }
        )

    }

    private fun showLoading() {
        progressBar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        progressBar.visibility = View.GONE
    }

    private fun renderPlaylists(playlists: PagedList<PlaylistEntity>) {
        if (playlists.isEmpty()) {
            playlistsRecyclerView.visibility = View.GONE
            noPlaylistTextView.visibility = View.VISIBLE
        } else {
            playlistsRecyclerView.visibility = View.VISIBLE
            playlistsAdapter.submitList(playlists)
        }
    }

    private fun renderError(message: String) {
        Snackbar.make(playlistsRecyclerView, message, Snackbar.LENGTH_LONG)
            .setAction(R.string.try_again) {
                playlistsViewModel.getPlaylists(categoryId ?: "")
            }
            .show()
    }

}

