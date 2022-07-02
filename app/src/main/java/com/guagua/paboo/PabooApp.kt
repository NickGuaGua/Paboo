package com.guagua.paboo

import android.app.Application
import com.guagua.data.PabooSdk
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class PabooApp: Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}