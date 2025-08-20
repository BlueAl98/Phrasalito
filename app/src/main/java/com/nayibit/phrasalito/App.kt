package com.nayibit.phrasalito

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.WorkManager
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class App: Application(){
    @Inject lateinit var workerFactory: HiltWorkerFactory

    companion object {
        var context: Context? = null
        var shareInstance: App? = null
    }

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        shareInstance = this
        context = this

        WorkManager.initialize(
            this, Configuration.Builder().setWorkerFactory(
                workerFactory
            ).build()
        )

    }

    @Synchronized
    fun getInstance(): App {
        return shareInstance!!
    }

}