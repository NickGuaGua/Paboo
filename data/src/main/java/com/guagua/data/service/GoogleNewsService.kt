package com.guagua.data.service

import com.guagua.data.bean.ArticlesResponseBean
import com.guagua.data.bean.SourcesResponseBean
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

internal interface GoogleNewsService {

    @GET("/v2/top-headlines")
    suspend fun getTopHeadlines(
        @Query("country") country: String?,
        @Query("category") category: String?,
        @Query("q") query: String?,
        @Query("pageSize") pageSize: Int?,
        @Query("page") page: Int?
    ): Response<ArticlesResponseBean>

    @GET("/v2/top-headlines/sources")
    suspend fun getSources(
        @Query("category") category: String?,
        @Query("language") language: String?,
        @Query("country") country: String?
    ): Response<SourcesResponseBean>

    @GET("/v2/everything")
    suspend fun searchNews(
        @Query("q") query: String?,
        @Query("searchIn") searchIn: String?,
        @Query("sources") source: String?,
        @Query("domains") domains: String?,
        @Query("excludeDomains") excludeDomains: String?,
        @Query("from") from: String?,
        @Query("to") to: String?,
        @Query("language") language: String?,
        @Query("sortBy") sortBy: String?,
        @Query("pageSize") pageSize: Int?,
        @Query("page") page: Int?
    ): Response<ArticlesResponseBean>
}