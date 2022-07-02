package com.guagua.paboo.domain

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.guagua.paboo.data.paging.SearchNewsPagingSource
import com.guagua.paboo.data.paging.SearchQuery
import com.guagua.paboo.data.repository.PabooRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SearchNewsUseCase @Inject constructor(
    private val repository: PabooRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend operator fun invoke(
        searchQuery: SearchQuery,
        pageSize: Int = 5,
        page: Int = 1
    ) = withContext(dispatcher) {
        Pager(
            PagingConfig(pageSize, enablePlaceholders = true, jumpThreshold = pageSize * 3)
        ) {
            SearchNewsPagingSource(repository, searchQuery, page)
        }.flow
    }
}