package ir.fallahpoor.tempo.search.view

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import ir.fallahpoor.tempo.R
import ir.fallahpoor.tempo.app.TempoApplication
import ir.fallahpoor.tempo.common.*
import ir.fallahpoor.tempo.common.itemdecoration.SpaceItemDecoration
import ir.fallahpoor.tempo.data.entity.SearchEntity
import ir.fallahpoor.tempo.data.entity.artist.ArtistEntity
import ir.fallahpoor.tempo.data.entity.common.ListEntity
import ir.fallahpoor.tempo.search.viewmodel.SearchViewModel
import kotlinx.android.synthetic.main.fragment_search.*
import javax.inject.Inject

class SearchFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val searchViewModel: SearchViewModel by viewModels { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_search, container, false)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        injectViewModelFactory()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupSearchView()
        observeViewModel()
    }

    private fun injectViewModelFactory() {
        (activity?.application as TempoApplication).appComponent.inject(this)
    }

    private fun setupSearchView() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    hideKeyboard()
                    showLoading()
                    searchViewModel.search(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?) = true

        })
    }

    private fun hideKeyboard() {
        val imm: InputMethodManager =
            context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.rootView?.windowToken, 0)
        view?.clearFocus()
    }

    private fun observeViewModel() {
        searchViewModel.getViewState().observe(
            viewLifecycleOwner,
            Observer { viewState ->
                when (viewState) {
                    is LoadingState -> showLoading()
                    is DataLoadedState -> renderSearchResult(viewState.data)
                    is ErrorState -> renderError(viewState.errorMessage)
                }
            })
    }

    private fun showLoading() {
        progressBar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        progressBar.visibility = View.GONE
    }

    private fun renderSearchResult(searchEntity: SearchEntity) {
        hideLoading()
        if (noSearchResult(searchEntity)) {
            noSearchResultTextView.fadeIn()
            searchScrollView.fadeOut()
        } else {
            noSearchResultTextView.fadeOut()
            searchResultsLinearLayout.removeAllViews()
            addRecyclerView(searchEntity.artists, SearchAdapter.Type.ARTIST, R.string.artists)
            addRecyclerView(searchEntity.albums, SearchAdapter.Type.ALBUM, R.string.albums)
            addRecyclerView(searchEntity.tracks, SearchAdapter.Type.TRACK, R.string.tracks)
            addRecyclerView(searchEntity.playlists, SearchAdapter.Type.PLAYLIST, R.string.playlists)
            searchScrollView.fadeIn()
        }
    }

    private fun <T> addRecyclerView(
        data: ListEntity<T>,
        type: SearchAdapter.Type, @StringRes title: Int
    ) {

        if (data.items.isNullOrEmpty()) {
            return
        }

        val view: View =
            layoutInflater.inflate(
                R.layout.list_item_search_result,
                searchResultsLinearLayout,
                false
            )

        setTitle(title, view)
        setVisibilityOfMoreTextView(data, view)
        setupRecyclerView(data, view, type)

        searchResultsLinearLayout.addView(view)

    }

    private fun setTitle(@StringRes title: Int, view: View) {
        view.findViewById<TextView>(R.id.searchResultTitleTextView).text = getString(title)
    }

    private fun <T> setVisibilityOfMoreTextView(data: ListEntity<T>, view: View) {
        if (data.items.size >= data.total) {
            view.findViewById<TextView>(R.id.moreSearchResultsTextView).visibility = View.GONE
        }
    }

    private fun <T> setupRecyclerView(data: ListEntity<T>, view: View, type: SearchAdapter.Type) {

        val searchResultRecyclerView =
            view.findViewById<RecyclerView>(R.id.searchResultRecyclerView)

        with(searchResultRecyclerView) {
            layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            setHasFixedSize(true)
            adapter = SearchAdapter(data, type) { t, imageView, textView ->
                val artistEntity = t as ArtistEntity
                val action = SearchFragmentDirections.actionToArtistFragment(
                    artistEntity.id,
                    artistEntity.name,
                    artistEntity.images[0].url,
                    artistEntity.uri
                )
                val extras = FragmentNavigatorExtras(
                    imageView to imageView.transitionName,
                    textView to textView.transitionName
                )
                findNavController().navigate(action, extras)
            }
            addItemDecoration(
                SpaceItemDecoration(
                    context,
                    8f,
                    SpaceItemDecoration.Orientation.HORIZONTAL
                )
            )
        }

    }

    private fun noSearchResult(searchEntity: SearchEntity): Boolean {
        return with(searchEntity) {
            albums.items.isNullOrEmpty() &&
                    artists.items.isNullOrEmpty() &&
                    tracks.items.isNullOrEmpty() &&
                    playlists.items.isNullOrEmpty()
        }
    }

    private fun renderError(message: String) {
        hideLoading()
        Snackbar.make(searchResultsLinearLayout, message, Snackbar.LENGTH_LONG)
            .setAction(R.string.try_again) {
                searchViewModel.search(searchView.query.toString())
            }
            .show()
    }

}
