package com.example.bareat_android.setup.extensions

import android.app.Activity
import android.content.Intent
import android.view.View
import android.widget.EditText

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

