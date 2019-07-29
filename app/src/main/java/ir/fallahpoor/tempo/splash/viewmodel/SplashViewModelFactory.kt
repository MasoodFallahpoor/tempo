package ir.fallahpoor.tempo.splash.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ir.fallahpoor.tempo.data.repository.authentication.AuthenticationRepository
import javax.inject.Inject

class SplashViewModelFactory @Inject
constructor(
    private val authenticationRepository: AuthenticationRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(viewModelClass: Class<T>): T {

        if (viewModelClass.isAssignableFrom(SplashViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SplashViewModel(authenticationRepository) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class")
        }

    }

}