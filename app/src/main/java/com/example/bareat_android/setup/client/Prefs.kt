package com.example.bareat_android.setup.client

import android.content.SharedPreferences

class Prefs(private val sharedPrefs: SharedPreferences){

    companion object {
        const val TOKEN = "TOKEN"
        const val USER_ID = "USER_ID"
        const val USER_EMAIL = "EMAIL"
        const val USER_NAME = "NAME"
        const val RESTAURANT_ID = "RESTAURANT_ID"
        const val FILTER = "FILTER"

    }

    var token: String?
        get() = sharedPrefs.getString(TOKEN, "")
        set(value) = sharedPrefs.edit().putString(TOKEN, value).apply()

    var email: String?
        get() = sharedPrefs.getString(USER_EMAIL, "")
        set(value) = sharedPrefs.edit().putString(USER_EMAIL, value).apply()

    var name: String?
        get() = sharedPrefs.getString(USER_NAME, "")
        set(value) = sharedPrefs.edit().putString(USER_NAME, value).apply()

    var id: String?
        get() = sharedPrefs.getString(USER_ID, "")
        set(value) = sharedPrefs.edit().putString(USER_ID, value).apply()

    var restaurantId: String?
        get() = sharedPrefs.getString(RESTAURANT_ID, "")
        set(value) = sharedPrefs.edit().putString(RESTAURANT_ID, value).apply()

    private fun remove(key: String) {
        sharedPrefs.edit().remove(key).apply()
    }

    fun clearLogin() {
        remove(TOKEN)
        remove(USER_ID)
    }

}