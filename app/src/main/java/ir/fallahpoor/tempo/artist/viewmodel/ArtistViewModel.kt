package ir.fallahpoor.tempo.artist.viewmodel

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import ir.fallahpoor.tempo.common.*
import ir.fallahpoor.tempo.data.common.ExceptionHumanizer
import ir.fallahpoor.tempo.data.entity.artist.ArtistAllInfoEntity
import ir.fallahpoor.tempo.data.repository.artists.ArtistsRepository
import javax.inject.Inject

// FIXME: Don't fetch data if it's already fetched.

class ArtistViewModel
@Inject constructor(
    private val artistsRepository: ArtistsRepository
) : BaseViewModel<ArtistAllInfoEntity>() {

    private var artistAllInfoEntity: ArtistAllInfoEntity? = null

    fun getArtist(artistId: String) {

        setViewState(LoadingState())

        val d: Disposable = artistsRepository.getArtistAllInfo(artistId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { artistAllInfoEntity: ArtistAllInfoEntity ->
                    this.artistAllInfoEntity = artistAllInfoEntity
                    setViewState(DataLoadedState(artistAllInfoEntity))
                },
                { t: Throwable ->
                    val message: String = ExceptionHumanizer.getHumanizedErrorMessage(t)
                    setViewState(ErrorState(message))
                }
            )

        addDisposable(d)

    }

}