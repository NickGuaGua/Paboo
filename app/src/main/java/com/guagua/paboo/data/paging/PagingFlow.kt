package com.guagua.paboo.data.paging

import androidx.lifecycle.Transformations.map
import androidx.paging.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface PagingFlow<T : Any> {
    val flow: Flow<PagingData<T>>
    fun refresh()
}

@OptIn(ExperimentalPagingApi::class)
class PagingFlowImpl<Key: Any, Value : Any> (
    private val pagingSourceFactory: () -> PagingSource<Key, Value>,
    initKey: Key,
    pagingConfig: PagingConfig,
    remoteMediator: RemoteMediator<Key, Value>?
) : PagingFlow<Value> {
    private var pagingSource: PagingSource<Key, Value>? = null

    override val flow: Flow<PagingData<Value>> = Pager(pagingConfig, initKey, remoteMediator) {
        pagingSourceFactory().also { pagingSource = it }
    }.flow

    override fun refresh() {
        pagingSource?.invalidate()
    }
}