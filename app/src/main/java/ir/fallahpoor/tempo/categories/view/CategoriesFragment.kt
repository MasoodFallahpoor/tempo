package ir.fallahpoor.tempo.categories.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import ir.fallahpoor.tempo.R
import ir.fallahpoor.tempo.app.TempoApplication
import ir.fallahpoor.tempo.categories.model.Category
import ir.fallahpoor.tempo.categories.viewmodel.CategoriesViewModel
import ir.fallahpoor.tempo.categories.viewmodel.CategoriesViewModelFactory
import ir.fallahpoor.tempo.common.EndlessScrollListener
import ir.fallahpoor.tempo.common.viewstate.*
import kotlinx.android.synthetic.main.fragment_categories.*
import javax.inject.Inject

class CategoriesFragment : Fragment() {

    @Inject
    lateinit var categoriesViewModelFactory: CategoriesViewModelFactory
    private lateinit var categoriesViewModel: CategoriesViewModel
    private var categoriesAdapter = CategoriesAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_categories, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        injectViewModelFactory()
        setupRecyclerView()
        setupViewModel()
        subscribeToViewModel()
        categoriesViewModel.getCategories()
    }

    private fun injectViewModelFactory() {
        (activity?.application as TempoApplication).appComponent.inject(this)
    }

    private fun setupViewModel() {
        categoriesViewModel = ViewModelProviders.of(this, categoriesViewModelFactory)
            .get(CategoriesViewModel::class.java)
    }

    private fun subscribeToViewModel() {
        categoriesViewModel.getViewStateLiveData().observe(viewLifecycleOwner,
            Observer { viewState ->
                hideLoading()
                when (viewState) {
                    is LoadingViewState -> showLoading()
                    is DataErrorViewState -> renderError(viewState.getMessage())
                    is MoreDataErrorViewState -> renderMoreDataError(viewState.getMessage())
                    is MoreDataLoadedViewState<*> -> renderMoreCategories(viewState.data as List<Category>)
                    is DataLoadedViewState<*> -> renderCategories(viewState.data as List<Category>)
                }
            })
    }

    private fun setupRecyclerView() {
        with(categoriesRecyclerView) {
            layoutManager = GridLayoutManager(context, 2)
            adapter = categoriesAdapter
            addOnScrollListener(object : EndlessScrollListener() {
                override fun onLoadMore() {
                    categoriesViewModel.getMoreCategories()
                }
            })
        }
    }

    private fun renderMoreCategories(categories: List<Category>) {
        categoriesAdapter.addCategories(categories.minus(categoriesAdapter.getCategories()))
    }

    private fun showLoading() {
        progressBar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        progressBar.visibility = View.GONE
    }

    private fun renderCategories(categories: List<Category>) {
        categoriesAdapter = createCategoriesAdapter(categories)
        categoriesRecyclerView.adapter = categoriesAdapter
    }

    private fun createCategoriesAdapter(categories: List<Category>) =
        CategoriesAdapter(context!!, categories) { category, imageView, textView ->
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

    private fun renderError(message: String) {
        Snackbar.make(categoriesRecyclerView, message, Snackbar.LENGTH_LONG)
            .setAction(R.string.try_again) {
                categoriesViewModel.getCategories()
            }
            .show()
    }

    private fun renderMoreDataError(message: String) {
        Snackbar.make(categoriesRecyclerView, message, Snackbar.LENGTH_LONG)
            .setAction(R.string.try_again) {
                categoriesViewModel.getMoreCategories()
            }
            .show()
    }

}