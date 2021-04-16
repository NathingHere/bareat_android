package com.example.bareat_android.setup.client

import android.content.SharedPreferences

class Prefs(private val sharedPrefs: SharedPreferences){

    companion object {
        const val TOKEN = "TOKEN"
    }

    var token: String?
        get() = sharedPrefs.getString(TOKEN, "")
        set(value) = sharedPrefs.edit().putString(TOKEN, value).apply()

}