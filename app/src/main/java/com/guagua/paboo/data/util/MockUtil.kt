package com.guagua.paboo.data.util

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.guagua.paboo.data.model.Article
import com.guagua.paboo.data.model.Category
import com.guagua.paboo.data.model.Country
import com.guagua.paboo.data.model.Source

object MockUtil {
    fun getArticle(
        id: Int, country: Country = Country.TW, category: Category = Category.GENERAL
    ): Article {
        return Article(
                id = "id$id",
                source = Source("sourceId$id", "source$id"),
                author = "author$id",
                title = "title $id",
                description = "description $id",
                url = "https://www.google.com?q=$id",
                urlToImage = "https://picsum.photos/200",
                publishedAt = Long.MAX_VALUE,
                content = "content $id",
                country = country,
                category = category
            )
    }

    fun getArticles(
        total: Int = 10, country: Country = Country.TW, category: Category = Category.GENERAL
    ): List<Article> {
        return (1..total).map { getArticle(it, country, category) }
    }

    fun getArticlePagingFlow(size: Int) = Pager(PagingConfig(size), null) {
        object : PagingSource<Int, Article>() {
            override fun getRefreshKey(state: PagingState<Int, Article>): Int? = null
            override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
                return LoadResult.Page(getArticles(size), null, null)
            }
        }
    }.flow
}