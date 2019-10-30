package ir.fallahpoor.tempo.data.datasource.category

import androidx.lifecycle.MutableLiveData
import androidx.paging.PositionalDataSource
import ir.fallahpoor.tempo.data.common.ExceptionHumanizer
import ir.fallahpoor.tempo.data.common.State
import ir.fallahpoor.tempo.data.entity.category.CategoriesEnvelop
import ir.fallahpoor.tempo.data.entity.category.CategoryEntity
import ir.fallahpoor.tempo.data.webservice.CategoriesWebService
import retrofit2.Response
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

        val state =
            try {

                val response: Response<CategoriesEnvelop> =
                    categoriesWebService.getCategories(offset = 0, limit = params.pageSize)
                        .execute()

                if (response.isSuccessful) {
                    val categoriesEntity = response.body()!!.categoriesEntity
                    callback.onResult(categoriesEntity.items, 0, categoriesEntity.total)
                    State.LOADED
                } else {
                    State(State.Status.ERROR, response.message())
                }

            } catch (ex: Exception) {
                val message = ExceptionHumanizer.getHumanizedErrorMessage(ex)
                State(State.Status.ERROR, message)
            }

        setState(state)

    }

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<CategoryEntity>) {

        setState(State.LOADING_MORE)

        val state =
            try {
                val response: Response<CategoriesEnvelop> = categoriesWebService.getCategories(
                    offset = params.startPosition,
                    limit = params.loadSize
                ).execute()

                if (response.isSuccessful) {
                    callback.onResult(response.body()!!.categoriesEntity.items)
                    State.LOADED
                } else {
                    State(State.Status.ERROR_MORE, response.message())
                }

            } catch (ex: Exception) {
                val message = ExceptionHumanizer.getHumanizedErrorMessage(ex)
                State(State.Status.ERROR_MORE, message)
            }

        setState(state)

    }

    val stateLiveData = MutableLiveData<State>()

    private fun setState(state: State) {
        stateLiveData.postValue(state)
    }

}