package ir.fallahpoor.tempo.search.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ir.fallahpoor.tempo.common.ViewState
import ir.fallahpoor.tempo.common.extensions.map
import ir.fallahpoor.tempo.common.extensions.switchMap
import ir.fallahpoor.tempo.data.common.Resource
import ir.fallahpoor.tempo.data.entity.SearchEntity
import ir.fallahpoor.tempo.data.repository.search.SearchRepository
import javax.inject.Inject

class SearchViewModel
@Inject constructor(
    private val searchRepository: SearchRepository
) : ViewModel() {

    private val searchQueryLiveData = MutableLiveData<String>()

    val searchResult: LiveData<ViewState> =
        searchQueryLiveData.switchMap { searchQuery: String ->
            searchRepository.search(searchQuery)
        }.map { resource: Resource<SearchEntity> ->
            when (resource) {
                is Resource.Success -> ViewState.DataLoaded(resource.data)
                is Resource.Error -> ViewState.Error(resource.errorMessage)
            }
        }

    fun search(query: String) {
        searchQueryLiveData.value = query
    }

}
