package com.tdr.app.kimikoscanvas

import android.app.Application
import timber.log.Timber

class KimikosCanvasApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}