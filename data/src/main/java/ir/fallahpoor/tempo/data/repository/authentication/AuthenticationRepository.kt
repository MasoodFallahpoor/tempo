package ir.fallahpoor.tempo.data.repository.authentication

import androidx.lifecycle.LiveData
import ir.fallahpoor.tempo.data.Resource

interface AuthenticationRepository {

    fun getAccessToken() : LiveData<Resource<Unit>>

}