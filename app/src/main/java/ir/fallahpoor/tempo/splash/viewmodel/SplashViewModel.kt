package ir.fallahpoor.tempo.splash.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import ir.fallahpoor.tempo.common.ViewState
import ir.fallahpoor.tempo.common.extensions.map
import ir.fallahpoor.tempo.data.common.Resource
import ir.fallahpoor.tempo.data.repository.authentication.AuthenticationRepository
import javax.inject.Inject

class SplashViewModel
@Inject constructor(
    authenticationRepository: AuthenticationRepository
) : ViewModel() {

    val accessToken: LiveData<ViewState> =
        authenticationRepository.getAccessToken().map { resource: Resource<Unit> ->
            when (resource) {
                is Resource.Success -> ViewState.DataLoaded(Unit)
                is Resource.Error -> ViewState.Error(resource.errorMessage)
            }
        }

}