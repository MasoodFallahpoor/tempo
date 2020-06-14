package ir.fallahpoor.tempo.playlists.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import ir.fallahpoor.tempo.common.BaseViewModel
import ir.fallahpoor.tempo.common.DataLoadedState
import ir.fallahpoor.tempo.common.ErrorState
import ir.fallahpoor.tempo.common.LoadingState
import ir.fallahpoor.tempo.data.common.ExceptionHumanizer
import ir.fallahpoor.tempo.data.entity.common.ListEntity
import ir.fallahpoor.tempo.data.entity.playlist.PlaylistEntity
import ir.fallahpoor.tempo.data.repository.category.CategoriesRepository

class PlaylistsViewModel
@ViewModelInject constructor(
    private val categoriesRepository: CategoriesRepository
) : BaseViewModel<List<PlaylistEntity>>() {

    private companion object {
        const val LIMIT = 20
    }

    private var playlists = mutableListOf<PlaylistEntity>()
    private var totalItems = Int.MAX_VALUE
    private var offset = 0

    fun getPlaylists(categoryId: String) {
        if (playlists.isEmpty()) {
            _getPlaylists(categoryId)
        } else {
            setViewState(DataLoadedState(playlists))
        }
    }

    private fun _getPlaylists(categoryId: String) {

        setViewState(LoadingState())

        val d: Disposable = categoriesRepository.getPlaylists(categoryId, offset, LIMIT)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { p: ListEntity<PlaylistEntity> ->
                    playlists.addAll(p.items)
                    totalItems = p.total
                    offset += LIMIT
                    setViewState(DataLoadedState(p.items))
                },
                { t: Throwable ->
                    val message: String = ExceptionHumanizer.getHumanizedErrorMessage(t)
                    setViewState(ErrorState(message))
                }
            )

        addDisposable(d)

    }

    fun getMorePlaylists(categoryId: String) {
        if (morePlaylistsAvailable()) {
            _getPlaylists(categoryId)
        }
    }

    private fun morePlaylistsAvailable() = offset < totalItems

}