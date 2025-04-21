package com.developing.charityapplication

import android.app.Application
import android.content.Context
import android.util.Log
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class HeartBellApplication : Application() {
    companion object {
        private lateinit var instance: HeartBellApplication

        fun getAppContext(): Context {
            return instance.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        Thread.setDefaultUncaughtExceptionHandler { thread, throwable ->
            Log.e("AppCrash", "Uncaught: ${throwable.message}", throwable)
        }
    }
}