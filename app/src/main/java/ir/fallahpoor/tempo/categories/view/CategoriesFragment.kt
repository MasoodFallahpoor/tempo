package ir.fallahpoor.tempo.categories.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.paging.PagedList
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import ir.fallahpoor.tempo.R
import ir.fallahpoor.tempo.app.TempoApplication
import ir.fallahpoor.tempo.categories.viewmodel.CategoriesViewModel
import ir.fallahpoor.tempo.common.ViewModelFactory
import ir.fallahpoor.tempo.data.common.State
import ir.fallahpoor.tempo.data.entity.category.CategoryEntity
import kotlinx.android.synthetic.main.fragment_categories.*
import javax.inject.Inject

class CategoriesFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var categoriesViewModel: CategoriesViewModel
    private lateinit var categoriesAdapter: CategoriesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_categories, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        injectViewModelFactory()
        setupAdapter()
        setupRecyclerView()
        setupViewModel()
        observeViewModel()
    }

    private fun injectViewModelFactory() {
        (activity?.application as TempoApplication).appComponent.inject(this)
    }

    private fun setupAdapter() {
        categoriesAdapter = CategoriesAdapter(context!!) { category, imageView, textView ->
            val action = CategoriesFragmentDirections.actionToPlaylistsFragment(
                category.id,
                category.name,
                category.icons[0].url
            )
            val extras = FragmentNavigatorExtras(
                imageView to imageView.transitionName,
                textView to textView.transitionName
            )
            findNavController().navigate(action, extras)
        }
    }

    private fun setupRecyclerView() {
        with(categoriesRecyclerView) {
            layoutManager = GridLayoutManager(context, 2)
            adapter = categoriesAdapter
            setHasFixedSize(true)
        }
    }

    private fun setupViewModel() {
        categoriesViewModel = ViewModelProvider(this, viewModelFactory)
            .get(CategoriesViewModel::class.java)
    }

    private fun observeViewModel() {

        categoriesViewModel.categories.observe(
            viewLifecycleOwner,
            Observer { categories: PagedList<CategoryEntity> ->
                categoriesAdapter.submitList(categories)
            }
        )

        categoriesViewModel.state.observe(
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

    private fun renderError(message: String) {
        Snackbar.make(categoriesRecyclerView, message, Snackbar.LENGTH_LONG)
            .setAction(R.string.try_again) {
                // TODO what to do here?
            }
            .show()
    }

}