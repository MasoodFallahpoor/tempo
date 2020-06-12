package ir.fallahpoor.tempo.data.repository.authentication

import io.reactivex.Completable

interface AuthenticationRepository {
    fun getAccessToken(): Completable
}