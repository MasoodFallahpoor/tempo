package ir.fallahpoor.tempo.categories.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import ir.fallahpoor.tempo.R
import ir.fallahpoor.tempo.app.TempoApplication
import ir.fallahpoor.tempo.categories.viewmodel.CategoriesViewModel
import ir.fallahpoor.tempo.common.*
import ir.fallahpoor.tempo.common.itemdecoration.SpaceItemDecoration
import kotlinx.android.synthetic.main.fragment_categories.*
import javax.inject.Inject

class CategoriesFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val categoriesViewModel: CategoriesViewModel by viewModels { viewModelFactory }
    private lateinit var categoriesAdapter: CategoriesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_categories, container, false)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        injectViewModelFactory()
    }

    private fun injectViewModelFactory() {
        (activity?.application as TempoApplication).appComponent.inject(this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupAdapter()
        setupRecyclerView()
        observeViewModel()
        categoriesViewModel.getCategories()
    }

    private fun setupAdapter() {
        categoriesAdapter = CategoriesAdapter { category, imageView, textView ->
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
            addItemDecoration(SpaceItemDecoration(requireContext(), 8f, 2))
            addOnScrollListener(object : EndlessScrollListener() {
                override fun onLoadMore() {
                    categoriesViewModel.getCategories()
                }
            })
        }
    }

    private fun observeViewModel() {
        categoriesViewModel.getViewState()
            .observe(viewLifecycleOwner,
                Observer { viewState ->
                    when (viewState) {
                        is LoadingState -> showLoading()
                        is DataLoadedState -> {
                            hideLoading()
                            categoriesAdapter.addCategories(viewState.data)
                            categoriesRecyclerView.fadeIn()
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
        progressBar.fadeIn()
    }

    private fun hideLoading() {
        progressBar.fadeOut()
    }

    private fun showErrorSnackbar(message: String) {
        Snackbar.make(categoriesRecyclerView, message, Snackbar.LENGTH_INDEFINITE)
            .setAction(R.string.try_again) {
                categoriesViewModel.getCategories()
            }
            .show()
    }

}