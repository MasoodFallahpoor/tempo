package ir.fallahpoor.tempo.search.view

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import ir.fallahpoor.tempo.R
import ir.fallahpoor.tempo.app.TempoApplication
import ir.fallahpoor.tempo.common.ViewModelFactory
import ir.fallahpoor.tempo.common.itemdecoration.SpaceItemDecoration
import ir.fallahpoor.tempo.common.viewstate.DataErrorViewState
import ir.fallahpoor.tempo.common.viewstate.DataLoadedViewState
import ir.fallahpoor.tempo.data.entity.SearchEntity
import ir.fallahpoor.tempo.data.entity.common.ListEntity
import ir.fallahpoor.tempo.search.viewmodel.SearchViewModel
import kotlinx.android.synthetic.main.fragment_search.*
import javax.inject.Inject

class SearchFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var searchViewModel: SearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_search, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        injectViewModelFactory()
        setupSearchView()
        setupViewModel()
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

    private fun setupViewModel() {
        searchViewModel = ViewModelProvider(this, viewModelFactory)
            .get(SearchViewModel::class.java)
    }

    private fun observeViewModel() {
        searchViewModel.searchResult.observe(
            viewLifecycleOwner,
            Observer { viewState ->
                hideLoading()
                when (viewState) {
                    is DataLoadedViewState<*> -> renderSearchResult(viewState.data as SearchEntity)
                    is DataErrorViewState -> renderError(viewState.getMessage())
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
        if (noSearchResult(searchEntity)) {
            noSearchResultTextView.visibility = View.VISIBLE
            searchResultsLinearLayout.visibility = View.GONE
        } else {
            noSearchResultTextView.visibility = View.GONE
            searchResultsLinearLayout.removeAllViews()
            searchResultsLinearLayout.visibility = View.VISIBLE
            addRecyclerView(searchEntity.artists, SearchAdapter.Type.ARTIST, R.string.artists)
            addRecyclerView(searchEntity.albums, SearchAdapter.Type.ALBUM, R.string.albums)
            addRecyclerView(searchEntity.tracks, SearchAdapter.Type.TRACK, R.string.tracks)
            addRecyclerView(searchEntity.playlists, SearchAdapter.Type.PLAYLIST, R.string.playlists)
        }
    }

    private fun <T> addRecyclerView(data: ListEntity<T>, type: SearchAdapter.Type, @StringRes title: Int) {

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
        val titleTextView =
            view.findViewById<TextView>(R.id.searchResultTitleTextView)
        titleTextView.text = getString(title)
    }

    private fun <T> setVisibilityOfMoreTextView(
        data: ListEntity<T>,
        view: View
    ) {
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
            adapter = SearchAdapter(context, data, type)
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
        Snackbar.make(searchResultsLinearLayout, message, Snackbar.LENGTH_LONG)
            .setAction(R.string.try_again) {
                showLoading()
                searchViewModel.search(searchView.query.toString())
            }
            .show()
    }

}
