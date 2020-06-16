package ir.fallahpoor.tempo.data.repository.search

import io.reactivex.Single
import ir.fallahpoor.tempo.data.entity.SearchEntity
import ir.fallahpoor.tempo.data.webservice.SearchWebService
import javax.inject.Inject

class SearchRepositoryImpl
@Inject constructor(
    private val searchWebService: SearchWebService
) : SearchRepository {

    companion object {
        private const val OFFSET = 0
        private const val LIMIT = 20
        private const val TYPE = "artist,album,track,playlist"
    }

    override fun search(query: String): Single<SearchEntity> =
        searchWebService.search(query, TYPE, OFFSET, LIMIT)

}