package ir.fallahpoor.tempo.playlists.viewmodel

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
import javax.inject.Inject

class PlaylistsViewModel
@Inject constructor(
    private val categoriesRepository: CategoriesRepository
) : BaseViewModel<List<PlaylistEntity>>() {

    private companion object {
        const val LIMIT = 20
    }

    private var totalItems = Int.MAX_VALUE
    private var offset = 0

    fun getPlaylists(categoryId: String) {

        if (morePlaylistsAvailable()) {

            setViewState(LoadingState())

            val d: Disposable = categoriesRepository.getPlaylists(categoryId, offset, LIMIT)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { p: ListEntity<PlaylistEntity> ->
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

    }

    private fun morePlaylistsAvailable() = offset < totalItems

}