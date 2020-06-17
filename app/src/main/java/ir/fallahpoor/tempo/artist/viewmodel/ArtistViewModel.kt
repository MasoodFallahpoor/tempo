package ir.fallahpoor.tempo.artist.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import ir.fallahpoor.tempo.common.BaseViewModel
import ir.fallahpoor.tempo.common.DataLoadedState
import ir.fallahpoor.tempo.common.ErrorState
import ir.fallahpoor.tempo.common.LoadingState
import ir.fallahpoor.tempo.data.common.ExceptionHumanizer
import ir.fallahpoor.tempo.data.entity.artist.ArtistAllInfoEntity
import ir.fallahpoor.tempo.data.repository.artists.ArtistsRepository

class ArtistViewModel
@ViewModelInject constructor(
    private val artistsRepository: ArtistsRepository
) : BaseViewModel<ArtistAllInfoEntity>() {

    private var dataFetched = false
    private var artistAllInfoEntity: ArtistAllInfoEntity? = null

    fun getArtist(artistId: String) {

        if (dataFetched) {
            setViewState(DataLoadedState(artistAllInfoEntity!!))
        } else {

            val d: Disposable = artistsRepository.getArtistAllInfo(artistId)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    setViewState(LoadingState())
                }
                .subscribe(
                    { artistAllInfoEntity: ArtistAllInfoEntity ->
                        this.artistAllInfoEntity = artistAllInfoEntity
                        dataFetched = true
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

}