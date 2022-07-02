package com.guagua.data.di

import com.guagua.data.source.NewsDataSource
import com.guagua.data.source.NewsDataSourceImpl
import com.guagua.data.service.GoogleNewsService
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

internal object NetworkModule {

    private const val cacheSize = 10 * 1024 * 1024L // 10MB
    private const val cachePath = "http-cache"

    fun provideNewsDataSource(
        host: String,
        apiKey: String,
        cacheDir: File?,
        isDebug: Boolean
    ): NewsDataSource {
        val okHttpClient = provideOkHttpClient(apiKey, cacheDir, isDebug)
        val retrofit = provideRetrofit(host, okHttpClient)
        val googleNewsService = retrofit.create(GoogleNewsService::class.java)
        return NewsDataSourceImpl(googleNewsService)
    }

    private fun provideRetrofit(host: String, okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(host)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    private fun provideOkHttpClient(
        key: String, cacheDir: File?, isDebug: Boolean
    ): OkHttpClient = OkHttpClient.Builder().apply {
        addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("X-Api-Key", key).build()
            chain.proceed(request)
        }
        addNetworkInterceptor(HttpLoggingInterceptor().apply {
            level = if (isDebug) HttpLoggingInterceptor.Level.BODY
            else HttpLoggingInterceptor.Level.NONE
        })
        cacheDir?.let {
            val cacheDirectory = File(it, cachePath)
            cache(Cache(cacheDirectory, cacheSize))
        }
    }.build()
}