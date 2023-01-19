package com.pashacabu.testapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class TestApp : Application(){

    override fun onCreate() {
        super.onCreate()
        initiateTimber()
    }

    private fun initiateTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}