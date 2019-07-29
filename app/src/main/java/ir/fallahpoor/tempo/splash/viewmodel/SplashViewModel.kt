package ir.fallahpoor.tempo.splash.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import ir.fallahpoor.tempo.common.DataErrorViewState
import ir.fallahpoor.tempo.common.DataLoadedViewState
import ir.fallahpoor.tempo.common.ViewState
import ir.fallahpoor.tempo.data.Resource
import ir.fallahpoor.tempo.data.repository.authentication.AuthenticationRepository

class SplashViewModel(
    private val authenticationRepository: AuthenticationRepository
) : ViewModel() {

    fun getAccessToken(): LiveData<ViewState> {
        return Transformations.map(authenticationRepository.getAccessToken()) { resource ->
            if (resource.status == Resource.Status.SUCCESS) {
                DataLoadedViewState(Unit)
            } else {
                DataErrorViewState(resource.error!!.message)
            }
        }
    }

}