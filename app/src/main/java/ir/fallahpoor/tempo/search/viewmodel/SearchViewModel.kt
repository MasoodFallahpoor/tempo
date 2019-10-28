package ir.fallahpoor.tempo.search.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import ir.fallahpoor.tempo.common.viewstate.DataErrorViewState
import ir.fallahpoor.tempo.common.viewstate.DataLoadedViewState
import ir.fallahpoor.tempo.common.viewstate.ViewState
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
        Transformations.map(
            Transformations.switchMap(searchQueryLiveData) {
                searchRepository.search(it)
            },
            ::transformResourceToViewState
        )

    fun search(query: String) {
        searchQueryLiveData.value = query
    }

    private fun transformResourceToViewState(resource: Resource<SearchEntity>): ViewState =
        when (resource) {
            is Resource.Success -> DataLoadedViewState(resource.data)
            is Resource.Error -> DataErrorViewState(resource.errorMessage)
        }

    override fun onCleared() {
        super.onCleared()
        searchRepository.dispose()
    }

}