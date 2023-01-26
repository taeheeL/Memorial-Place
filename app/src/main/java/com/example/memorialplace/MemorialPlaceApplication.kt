package com.example.memorialplace

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import timber.log.Timber

class MemorialPlaceApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
}