package ir.fallahpoor.tempo.splash.viewmodel

import io.reactivex.disposables.Disposable
import ir.fallahpoor.tempo.common.*
import ir.fallahpoor.tempo.data.common.ExceptionHumanizer
import ir.fallahpoor.tempo.data.repository.authentication.AuthenticationRepository
import javax.inject.Inject

class SplashViewModel
@Inject constructor(
    private val authenticationRepository: AuthenticationRepository
) : BaseViewModel<Unit>() {

    fun getAccessToken() {

        setViewState(LoadingState())

        val d: Disposable = authenticationRepository.getAccessToken()
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