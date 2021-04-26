package com.example.bareat_android.setup.extensions

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.EditText
import com.example.bareat_android.setup.client.Prefs.Companion.FILTER
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.regex.Pattern

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

//LOGIN - REGISTER

fun String.isEmail(): Boolean {
    val matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(this)
    return matcher.find()
}

fun String.isValidPassword(): Boolean {
    return this.length >= 6
}

fun String.isPhoneNumber(): Boolean {
    val matcher = VALID_PHONE_REGEX.matcher(this)
    return matcher.find()
}

private val VALID_EMAIL_ADDRESS_REGEX = Pattern.compile(
        "^([_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*(\\.[a-zA-Z]{1,6}))?$",
        Pattern.CASE_INSENSITIVE
)

private val VALID_PASS_REGEX = Pattern.compile(
        "((?=.*\\d)(?=.*[a-z]).{6,20})"
)

private val VALID_PHONE_REGEX = Pattern.compile(
        "^(\\+{1}[02-9]{2}([0-9]{9}))|(\\+{1}[1]{1}([0-9]{9}))\$",
        Pattern.CASE_INSENSITIVE
)

