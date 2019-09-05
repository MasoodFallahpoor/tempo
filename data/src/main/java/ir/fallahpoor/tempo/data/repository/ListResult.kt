package ir.fallahpoor.tempo.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import ir.fallahpoor.tempo.data.common.State

data class ListResult<T>(
    val data: LiveData<PagedList<T>>,
    val state: LiveData<State>
)