package com.guagua.data

import com.guagua.data.di.NetworkModule
import java.io.File

object PabooSdk {
    fun getNewsDataSource(
        host: String,
        apiKey: String,
        cacheDir: File?,
        isDebug: Boolean = false
    ) = synchronized(Unit) {
        NetworkModule.provideNewsDataSource(host, apiKey, cacheDir, isDebug)
    }
}