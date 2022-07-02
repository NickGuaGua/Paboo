package com.guagua.data.source

import com.guagua.data.bean.ArticlesResponseBean
import com.guagua.data.bean.SourcesResponseBean
import com.guagua.data.bean.base.ResponseBean
import retrofit2.Response
import kotlin.jvm.Throws

import com.guagua.data.service.GoogleNewsService

interface NewsDataSource {

    suspend fun getTopHeadlines(
        country: String?,
        category: String?,
        query: String?,
        pageSize: Int?,
        page: Int?
    ): ArticlesResponseBean

    suspend fun getSources(
        category: String?,
        language: String?,
        country: String?
    ): SourcesResponseBean

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
        page: Int?
    ): ArticlesResponseBean
}

internal class NewsDataSourceImpl(
    private val googleNewsService: GoogleNewsService
) : NewsDataSource {

    override suspend fun getTopHeadlines(
        country: String?,
        category: String?,
        query: String?,
        pageSize: Int?,
        page: Int?
    ): ArticlesResponseBean = request {
        googleNewsService.getTopHeadlines(country, category, query, pageSize, page)
    }

    override suspend fun getSources(
        category: String?,
        language: String?,
        country: String?
    ): SourcesResponseBean = request {
        googleNewsService.getSources(category, language, country)
    }

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
        page: Int?
    ): ArticlesResponseBean = request {
        googleNewsService.searchNews(
            query,
            searchIn,
            source,
            domains,
            excludeDomains,
            from,
            to,
            language,
            sortBy,
            pageSize,
            page
        )
    }

    @Throws
    private inline fun <reified T : ResponseBean> request(block: () -> Response<T>): T {
        val response = block.invoke()

        if (!response.isSuccessful) {
            throw error(response.errorBody()?.string() ?: "API response error.")
        }

        return response.body()?.let {
            it.takeUnless { it.status != "ok" } ?: error("${it.code}: ${it.message}")
        } ?: error("Null body")
    }
}