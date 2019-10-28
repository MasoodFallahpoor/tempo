package ir.fallahpoor.tempo.splash.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import ir.fallahpoor.tempo.common.viewstate.DataErrorViewState
import ir.fallahpoor.tempo.common.viewstate.DataLoadedViewState
import ir.fallahpoor.tempo.common.viewstate.ViewState
import ir.fallahpoor.tempo.data.common.Resource
import ir.fallahpoor.tempo.data.repository.authentication.AuthenticationRepository
import javax.inject.Inject

class SplashViewModel
@Inject constructor(
    private val authenticationRepository: AuthenticationRepository
) : ViewModel() {

    fun getAccessToken(): LiveData<ViewState> {
        return Transformations.map(authenticationRepository.getAccessToken()) { resource ->
            when (resource) {
                is Resource.Success -> DataLoadedViewState(Unit)
                is Resource.Error -> DataErrorViewState(resource.errorMessage)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        authenticationRepository.dispose()
    }

}