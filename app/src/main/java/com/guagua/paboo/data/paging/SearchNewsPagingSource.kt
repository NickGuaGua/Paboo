package com.guagua.paboo.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.guagua.paboo.data.model.Article
import com.guagua.paboo.data.repository.PabooRepository

class SearchNewsPagingSource(
    private val repository: PabooRepository,
    private val searchQuery: SearchQuery,
    private val initialKey: Int,
): PagingSource<Int, Article>() {

    override val jumpingSupported: Boolean = true

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        return try {
            val key = params.key ?: initialKey
            val response = with(searchQuery) {
                repository.searchNews(query, searchIn, source, domains, excludeDomains, from, to, language, sortBy, params.loadSize, key)
            }

            val itemsBefore = (key - 1) * params.loadSize
            val itemsAfter = response.totalResults - (itemsBefore + response.articles.size)

            LoadResult.Page(
                data = response.articles,
                prevKey = if (key > 1) key - 1 else null,
                nextKey = if (itemsAfter <= 0) null else key + 1,
                itemsBefore = itemsBefore,
                itemsAfter = itemsAfter
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}