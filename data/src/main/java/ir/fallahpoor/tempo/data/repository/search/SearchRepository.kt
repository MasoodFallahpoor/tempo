package ir.fallahpoor.tempo.data.repository.search

import io.reactivex.Single
import ir.fallahpoor.tempo.data.entity.SearchEntity

interface SearchRepository {
    fun search(query: String): Single<SearchEntity>
}