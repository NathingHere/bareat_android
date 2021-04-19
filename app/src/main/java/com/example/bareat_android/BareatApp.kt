package com.example.bareat_android

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.example.bareat_android.setup.koin.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class BareatApp : Application() {

    override fun onCreate() {
        super.onCreate()

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        startKoin {
            androidContext(this@BareatApp)
            androidLogger()
            modules(listOf(appModule, domainModule, networkModule, useCaseModule, viewModelModule))
        }
    }
}