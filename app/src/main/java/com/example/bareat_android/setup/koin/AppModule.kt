package com.example.bareat_android.setup.koin

import android.content.Context
import android.content.SharedPreferences
import com.example.bareat_android.BuildConfig
import com.example.bareat_android.setup.client.Prefs
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {

    single<SharedPreferences> {
        androidContext().getSharedPreferences("${BuildConfig.APPLICATION_ID}.prefs", Context.MODE_PRIVATE)
    }

    single { Prefs(get()) }

}