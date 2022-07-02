package com.guagua.paboo.data.room.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.guagua.data.source.NewsDataSource
import com.guagua.paboo.data.model.Article
import com.guagua.paboo.data.model.Category
import com.guagua.paboo.data.model.CategoryNewsKey
import com.guagua.paboo.data.model.Country
import com.guagua.paboo.data.room.PabooDatabase
import timber.log.Timber
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalPagingApi::class)
class NewsMediator(
    private val country: Country,
    private val category: Category,
    private val database: PabooDatabase,
    private val newsDataSource: NewsDataSource
) : RemoteMediator<Int, Article>() {

    private val initPage = 1
    private val newsDao = database.newsDao()
    private val remoteKeyDao = database.categoryKeyDao()
    private var lastUpdateTime: Long = 0L

    override suspend fun initialize(): InitializeAction {
        val cacheTimeout = TimeUnit.MILLISECONDS.convert(10, TimeUnit.MINUTES)
        val timeInterval = System.currentTimeMillis() - lastUpdateTime
        return if (timeInterval >= cacheTimeout) {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        } else {
            InitializeAction.SKIP_INITIAL_REFRESH
        }
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Article>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> initPage
                LoadType.APPEND -> {
                    (remoteKeyDao.queryKey(country, category)?.page ?: 0) + 1
                }
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
            }

            val response = newsDataSource.getTopHeadlines(
                country.name,
                category.name,
                null,
                state.config.pageSize,
                loadKey
            ).also { lastUpdateTime = System.currentTimeMillis() }

            val isEndOfPaginationReached = (response.totalResults ?: 0) <= state.config.pageSize * loadKey

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    remoteKeyDao.deleteKeys(country, category)
                    newsDao.deleteArticles(country, category)
                }

                if (isEndOfPaginationReached) {
                    remoteKeyDao.deleteKeys(country, category)
                } else {
                    remoteKeyDao.insertOrReplace(CategoryNewsKey(country, category, loadKey))
                }
                newsDao.insertArticles(response.articles.map { Article.create(it, country, category) })
            }

            MediatorResult.Success(
                endOfPaginationReached = isEndOfPaginationReached
            )
        } catch (e: Exception) {
            resetCache()
            MediatorResult.Error(e)
        }
    }

    private fun resetCache() {
        lastUpdateTime = 0L
    }
}