package ir.fallahpoor.tempo.playlists.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.transition.TransitionInflater
import coil.api.load
import com.google.android.material.snackbar.Snackbar
import ir.fallahpoor.tempo.R
import ir.fallahpoor.tempo.app.TempoApplication
import ir.fallahpoor.tempo.common.*
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
    private val playlistsViewModel: PlaylistsViewModel by viewModels { viewModelFactory }
    private lateinit var playlistsAdapter: PlaylistsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        val transition = TransitionInflater.from(context)
            .inflateTransition(android.R.transition.move)
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
        categoryIconImageView.load(categoryIconUrl!!)

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        injectViewModelFactory()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupAdapter()
        setupRecyclerView()
        observeViewModel()
        playlistsViewModel.getPlaylists(categoryId ?: "")
    }

    private fun injectViewModelFactory() {
        (activity?.application as TempoApplication).appComponent.inject(this)
    }

    private fun setupAdapter() {
        playlistsAdapter = PlaylistsAdapter()
    }

    private fun setupRecyclerView() {
        with(playlistsRecyclerView) {
            layoutManager = GridLayoutManager(context, 2)
            adapter = playlistsAdapter
            setHasFixedSize(true)
            addOnScrollListener(object : EndlessScrollListener() {
                override fun onLoadMore() {
                    playlistsViewModel.getPlaylists(categoryId ?: "")
                }
            })
        }
    }

    private fun observeViewModel() {

        playlistsViewModel.getViewState().observe(
            viewLifecycleOwner,
            Observer { viewState ->
                when (viewState) {
                    is LoadingState -> showLoading()
                    is DataLoadedState -> {
                        hideLoading()
                        renderPlaylists(viewState.data)
                    }
                    is ErrorState -> {
                        hideLoading()
                        showErrorSnackbar(viewState.errorMessage)
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

    private fun renderPlaylists(playlists: List<PlaylistEntity>) {
        if (playlistsAdapter.itemCount == 0 && playlists.isEmpty()) {
            playlistsRecyclerView.visibility = View.GONE
            noPlaylistTextView.visibility = View.VISIBLE
        } else {
            playlistsRecyclerView.visibility = View.VISIBLE
            playlistsAdapter.addPlaylists(playlists)
        }
    }

    private fun showErrorSnackbar(message: String) {
        Snackbar.make(playlistsRecyclerView, message, Snackbar.LENGTH_LONG)
            .setAction(R.string.try_again) {
                playlistsViewModel.getPlaylists(categoryId ?: "")
            }
            .show()
    }

}

