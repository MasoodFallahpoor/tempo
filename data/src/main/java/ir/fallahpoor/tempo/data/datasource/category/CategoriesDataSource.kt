package ir.fallahpoor.tempo.data.datasource.category

import androidx.lifecycle.MutableLiveData
import androidx.paging.PositionalDataSource
import ir.fallahpoor.tempo.data.common.State
import ir.fallahpoor.tempo.data.entity.category.CategoriesEnvelop
import ir.fallahpoor.tempo.data.entity.category.CategoryEntity
import ir.fallahpoor.tempo.data.webservice.CategoriesWebService
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class CategoriesDataSource
@Inject constructor(
    private val categoriesWebService: CategoriesWebService
) : PositionalDataSource<CategoryEntity>() {

    override fun loadInitial(
        params: LoadInitialParams,
        callback: LoadInitialCallback<CategoryEntity>
    ) {

        setState(State.LOADING)

        try {
            val response: Response<CategoriesEnvelop> =
                categoriesWebService.getCategories(offset = 0, limit = params.pageSize)
                    .execute()

            val state = if (response.isSuccessful) {
                val categoriesEntity = response.body()!!.categoriesEntity
                callback.onResult(categoriesEntity.items, 0, categoriesEntity.total)
                State.LOADED
            } else {
                State(State.Status.ERROR, response.message())
            }

            setState(state)

        } catch (ex: Exception) {
            val message = getMessage(ex)
            val state = State(State.Status.ERROR, message)
            setState(state)
        }

    }

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<CategoryEntity>) {

        setState(State.LOADING_MORE)

        try {
            val response: Response<CategoriesEnvelop> = categoriesWebService.getCategories(
                offset = params.startPosition,
                limit = params.loadSize
            ).execute()

            val state: State = if (response.isSuccessful) {
                callback.onResult(response.body()!!.categoriesEntity.items)
                State.LOADED
            } else {
                State(State.Status.ERROR_MORE, response.message())
            }

            setState(state)

        } catch (ex: Exception) {
            val message = getMessage(ex)
            val state = State(State.Status.ERROR_MORE, message)
            setState(state)
        }

    }

    val stateLiveData = MutableLiveData<State>()

    private fun setState(state: State) {
        stateLiveData.postValue(state)
    }

    private fun getMessage(t: Throwable): String {
        return when (t) {
            is IOException -> NO_INTERNET_CONNECTION
            else -> SOMETHING_WENT_WRONG
        }
    }

    companion object {
        private const val NO_INTERNET_CONNECTION = "No Internet connection"
        private const val SOMETHING_WENT_WRONG = "Something went wrong"
    }

}