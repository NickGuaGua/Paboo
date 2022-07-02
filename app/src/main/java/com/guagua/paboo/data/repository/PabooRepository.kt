package com.guagua.paboo.data.repository

import androidx.paging.PagingConfig
import com.guagua.data.source.NewsDataSource
import com.guagua.paboo.data.room.mediator.NewsMediator
import com.guagua.paboo.data.model.*
import com.guagua.paboo.data.paging.PagingFlow
import com.guagua.paboo.data.paging.PagingFlowImpl
import com.guagua.paboo.data.room.PabooDatabase

interface PabooRepository {
    fun getTopHeadlinesPagingFlow(
        country: Country,
        category: Category,
        query: String?,
        page: Int,
        pageSize: Int
    ): PagingFlow<Article>

    suspend fun getSources(
        category: Category?,
        language: Language?,
        country: Country?
    ): SourcesResponse

    suspend fun searchNews(
        query: String?,
        searchIn: String?,
        source: String?,
        domains: String?,
        excludeDomains: String?,
        from: String?,
        to: String?,
        language: String?,
        sortBy: String?,
        pageSize: Int?,
        page: Int
    ): ArticleResponse
}

class PabooRepositoryImpl(
    private val remoteDataSource: NewsDataSource,
    private val database: PabooDatabase
): PabooRepository {

    private val cachedPagingFlow: MutableMap<Category, PagingFlow<Article>> = mutableMapOf()

    override fun getTopHeadlinesPagingFlow(
        country: Country,
        category: Category,
        query: String?,
        page: Int,
        pageSize: Int
    ): PagingFlow<Article> {
        return cachedPagingFlow.getOrPut(category) {
            PagingFlowImpl(
                pagingSourceFactory = {
                    database.newsDao().queryArticles(country, category)
                },
                initKey = page,
                pagingConfig = PagingConfig(pageSize = pageSize, enablePlaceholders = true),
                remoteMediator = NewsMediator(country, category, database, remoteDataSource)
            )
        }
    }

    override suspend fun getSources(
        category: Category?,
        language: Language?,
        country: Country?
    ): SourcesResponse = remoteDataSource.getSources(
        category?.name, language?.name, country?.name
    ).let { SourcesResponse.create(it) }

    override suspend fun searchNews(
        query: String?,
        searchIn: String?,
        source: String?,
        domains: String?,
        excludeDomains: String?,
        from: String?,
        to: String?,
        language: String?,
        sortBy: String?,
        pageSize: Int?,
        page: Int
    ): ArticleResponse = remoteDataSource.searchNews(
        query, searchIn, source, domains, excludeDomains, from, to, language, sortBy, pageSize, page
    ).let { ArticleResponse.create(it, null, null) }
}