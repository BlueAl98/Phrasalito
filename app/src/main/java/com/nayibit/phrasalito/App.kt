package com.nayibit.phrasalito

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App: Application(){
    companion object {
        var context: Context? = null
        var shareInstance: App? = null
    }

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        shareInstance = this
        context = this
    }

    @Synchronized
    fun getInstance(): App {
        return shareInstance!!
    }

}