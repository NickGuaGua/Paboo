package com.guagua.paboo.di

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import com.guagua.data.PabooSdk
import com.guagua.data.source.NewsDataSource
import com.guagua.paboo.BuildConfig
import com.guagua.paboo.data.repository.PabooRepository
import com.guagua.paboo.data.repository.PabooRepositoryImpl
import com.guagua.paboo.data.room.PabooDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class PabooModule {

    @Singleton
    @Provides
    fun providePabooRepository(remoteDataSource: NewsDataSource, database: PabooDatabase): PabooRepository {
        return PabooRepositoryImpl(remoteDataSource, database)
    }

    @Singleton
    @Provides
    fun provideNewsDataSource(@ApplicationContext context: Context): NewsDataSource {
        return PabooSdk.getNewsDataSource(
            "https://newsapi.org/",
            BuildConfig.GoogleNewsApiKey,
            context.cacheDir,
            BuildConfig.DEBUG
        )
    }

    @Provides
    fun provideIoDispatcher() = Dispatchers.IO

    @Singleton
    @Provides
    fun provideGson() = Gson()

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): PabooDatabase = Room.databaseBuilder(
        context,
        PabooDatabase::class.java,
        PabooDatabase::class.java.simpleName
    ).build()
}