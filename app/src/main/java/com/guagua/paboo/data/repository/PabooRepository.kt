package com.guagua.paboo.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.guagua.data.source.NewsDataSource
import com.guagua.paboo.data.room.mediator.NewsMediator
import com.guagua.paboo.data.model.*
import com.guagua.paboo.data.room.PabooDatabase
import kotlinx.coroutines.flow.Flow

interface PabooRepository {

    fun getTopHeadlinesFlow(
        country: Country,
        category: Category,
        query: String?,
        page: Int,
        pageSize: Int
    ): Flow<PagingData<Article>>

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

    @OptIn(ExperimentalPagingApi::class)
    override fun getTopHeadlinesFlow(
        country: Country,
        category: Category,
        query: String?,
        page: Int,
        pageSize: Int
    ): Flow<PagingData<Article>> {
        return Pager(
            PagingConfig(pageSize = pageSize, enablePlaceholders = true),
            page,
            NewsMediator(country, category, database, remoteDataSource)
        ) {
            database.newsDao().queryArticles(country, category)
        }.flow
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