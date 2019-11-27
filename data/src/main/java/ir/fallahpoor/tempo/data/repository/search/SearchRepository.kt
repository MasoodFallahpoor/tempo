package ir.fallahpoor.tempo.data.repository.search

import androidx.lifecycle.LiveData
import ir.fallahpoor.tempo.data.common.Resource
import ir.fallahpoor.tempo.data.entity.SearchEntity

interface SearchRepository {
    fun search(query: String): LiveData<Resource<SearchEntity>>
}