package ir.fallahpoor.tempo.splash.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import ir.fallahpoor.tempo.common.BaseViewModel
import ir.fallahpoor.tempo.common.DataLoadedState
import ir.fallahpoor.tempo.common.ErrorState
import ir.fallahpoor.tempo.common.LoadingState
import ir.fallahpoor.tempo.data.common.ExceptionHumanizer
import ir.fallahpoor.tempo.data.repository.authentication.AuthenticationRepository

class SplashViewModel
@ViewModelInject constructor(
    private val authenticationRepository: AuthenticationRepository
) : BaseViewModel<Unit>() {

    fun getAccessToken() {

        val d: Disposable = authenticationRepository.getAccessToken()
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                setViewState(LoadingState())
            }
            .subscribe(
                {
                    setViewState(DataLoadedState(Unit))
                },
                { t: Throwable ->
                    val message: String = ExceptionHumanizer.getHumanizedErrorMessage(t)
                    setViewState(ErrorState(message))
                }
            )

        addDisposable(d)

    }

}