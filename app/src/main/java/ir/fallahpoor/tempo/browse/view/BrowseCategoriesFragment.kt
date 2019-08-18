package ir.fallahpoor.tempo.browse.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import ir.fallahpoor.tempo.R
import ir.fallahpoor.tempo.browse.di.BrowseCategoriesModule
import ir.fallahpoor.tempo.browse.di.DaggerBrowseCategoriesComponent
import ir.fallahpoor.tempo.browse.model.Category
import ir.fallahpoor.tempo.browse.viewmodel.BrowseCategoriesViewModel
import ir.fallahpoor.tempo.browse.viewmodel.BrowseCategoriesViewModelFactory
import ir.fallahpoor.tempo.common.*
import kotlinx.android.synthetic.main.fragment_browse_categories.*
import javax.inject.Inject

class BrowseCategoriesFragment : Fragment() {

    @Inject
    lateinit var browseCategoriesViewModelFactory: BrowseCategoriesViewModelFactory
    private lateinit var browseCategoriesViewModel: BrowseCategoriesViewModel
    private var categoriesAdapter = CategoriesAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_browse_categories, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        injectViewModelFactory()
        setupRecyclerView()
        setupViewModel()
        subscribeToViewModel()
        browseCategoriesViewModel.getCategories()
    }

    private fun injectViewModelFactory() {
        DaggerBrowseCategoriesComponent.builder()
            .browseCategoriesModule(BrowseCategoriesModule(activity!!))
            .build()
            .inject(this)
    }

    private fun setupViewModel() {
        browseCategoriesViewModel = ViewModelProviders.of(this, browseCategoriesViewModelFactory)
            .get(BrowseCategoriesViewModel::class.java)
    }

    private fun subscribeToViewModel() {
        browseCategoriesViewModel.getViewStateLiveData().observe(viewLifecycleOwner,
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
                    browseCategoriesViewModel.getMoreCategories()
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
        CategoriesAdapter(context!!, categories, null)

    private fun renderError(message: String) {
        Snackbar.make(categoriesRecyclerView, message, Snackbar.LENGTH_INDEFINITE)
            .setAction(R.string.try_again) {
                browseCategoriesViewModel.getCategories()
            }
            .show()
    }

    private fun renderMoreDataError(message: String) {
        Snackbar.make(categoriesRecyclerView, message, Snackbar.LENGTH_INDEFINITE)
            .setAction(R.string.try_again) {
                browseCategoriesViewModel.getMoreCategories()
            }
            .show()
    }

}
