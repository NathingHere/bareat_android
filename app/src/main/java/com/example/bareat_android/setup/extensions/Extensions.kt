package com.example.bareat_android.setup.extensions

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.EditText
import com.example.bareat_android.setup.client.Prefs.Companion.FILTER
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

fun EditText.getStringText() = text.toString()

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

inline fun <reified T : Any> Activity.launchActivity(finish: Boolean = false) {
    startActivity(Intent(this, T::class.java))
    if (finish) finish()
}

inline fun <reified T : Any> String.fromJson(): T {
    val gson = Gson()
    val type = object : TypeToken<T>() {}.type
    return gson.fromJson(this, type)
}

fun Any.logD(text: String) {
    Log.d("$FILTER: ${this::class.java.simpleName}", text)
}

