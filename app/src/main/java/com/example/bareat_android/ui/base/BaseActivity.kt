package com.example.bareat_android.ui.base

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.viewbinding.ViewBinding
import com.example.bareat_android.R
import com.example.bareat_android.setup.client.Prefs
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import org.koin.android.ext.android.inject

abstract class BaseActivity<BINDING : ViewBinding> : AppCompatActivity() {

    var progressDialog: Dialog? = null

    protected lateinit var binding: BINDING
    protected val navController: NavController? by lazy { findNavController(R.id.fragment) }
    val prefs by inject<Prefs>()

    abstract fun initializeBinding(): BINDING

    abstract fun initView()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(initializeBinding().root)

        window.apply {
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            statusBarColor = Color.TRANSPARENT
        }


        initView()
    }

    fun showError(error: String, v: View?) {
        v?.let {
            with(Snackbar.make(v, error, Snackbar.LENGTH_SHORT)) {
                view.setBackgroundColor(
                        ContextCompat.getColor(
                                context,
                                R.color.bareat_red
                        )
                )
                val tv = view.findViewById(R.id.snackbar_text) as TextView
                tv.setTextColor(ContextCompat.getColor(v.context, R.color.white))
                show()
            }
        }
    }

    fun showMessage(message: String, v: View?) {
        v?.let {
            with(Snackbar.make(v, message, Snackbar.LENGTH_SHORT)) {
                view.setBackgroundColor(
                        ContextCompat.getColor(
                                context,
                                R.color.bareat_blue
                        )
                )
                val tv = view.findViewById(R.id.snackbar_text) as TextView
                tv.setTextColor(ContextCompat.getColor(v.context, R.color.white))
                show()
            }
        }
    }

}