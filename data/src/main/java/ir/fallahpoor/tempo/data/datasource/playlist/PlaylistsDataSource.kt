package ir.fallahpoor.tempo.data.datasource.playlist

import androidx.lifecycle.MutableLiveData
import androidx.paging.PositionalDataSource
import ir.fallahpoor.tempo.data.common.ExceptionHumanizer
import ir.fallahpoor.tempo.data.common.State
import ir.fallahpoor.tempo.data.entity.playlist.PlaylistEntity
import ir.fallahpoor.tempo.data.entity.playlist.PlaylistsEnvelop
import ir.fallahpoor.tempo.data.webservice.CategoriesWebService
import retrofit2.Response
import javax.inject.Inject

class PlaylistsDataSource
@Inject constructor(
    private val categoriesWebService: CategoriesWebService
) : PositionalDataSource<PlaylistEntity>() {

    override fun loadInitial(
        params: LoadInitialParams,
        callback: LoadInitialCallback<PlaylistEntity>
    ) {

        setState(State.LOADING)

        try {
            val response: Response<PlaylistsEnvelop> =
                categoriesWebService.getPlaylists(
                    categoryId,
                    offset = 0,
                    limit = params.pageSize
                )
                    .execute()

            val state = if (response.isSuccessful) {
                val playlistsEntity = response.body()!!.playlistsEntity
                callback.onResult(playlistsEntity.items, 0, playlistsEntity.total)
                State.LOADED
            } else {
                State(State.Status.ERROR, response.message())
            }

            setState(state)

        } catch (ex: Exception) {
            val message = ExceptionHumanizer.getHumanizedErrorMessage(ex)
            val state = State(State.Status.ERROR, message)
            setState(state)
        }

    }

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<PlaylistEntity>) {

        setState(State.LOADING_MORE)

        try {
            val response: Response<PlaylistsEnvelop> = categoriesWebService.getPlaylists(
                categoryId,
                offset = params.startPosition,
                limit = params.loadSize
            ).execute()

            val state: State = if (response.isSuccessful) {
                callback.onResult(response.body()!!.playlistsEntity.items)
                State.LOADED
            } else {
                State(State.Status.ERROR_MORE, response.message())
            }

            setState(state)

        } catch (ex: Exception) {
            val message = ExceptionHumanizer.getHumanizedErrorMessage(ex)
            val state = State(State.Status.ERROR_MORE, message)
            setState(state)
        }

    }

    val stateLiveData = MutableLiveData<State>()
    var categoryId: String = ""

    private fun setState(state: State) {
        stateLiveData.postValue(state)
    }

}