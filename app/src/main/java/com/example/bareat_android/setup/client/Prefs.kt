package com.example.bareat_android.setup.client

import android.content.SharedPreferences

class Prefs(private val sharedPrefs: SharedPreferences){

    companion object {
        const val TOKEN = "TOKEN"
        const val EMAIL = "EMAIL"
        const val NAME = "NAME"
        const val SURNAME = "SURNAME"
        const val FILTER = "FILTER"
    }

    var token: String?
        get() = sharedPrefs.getString(TOKEN, "")
        set(value) = sharedPrefs.edit().putString(TOKEN, value).apply()

    var email: String?
        get() = sharedPrefs.getString(EMAIL, "")
        set(value) = sharedPrefs.edit().putString(EMAIL, value).apply()

    var name: String?
        get() = sharedPrefs.getString(NAME, "")
        set(value) = sharedPrefs.edit().putString(NAME, value).apply()

    var surname: String?
        get() = sharedPrefs.getString(SURNAME, "")
        set(value) = sharedPrefs.edit().putString(SURNAME, value).apply()

}