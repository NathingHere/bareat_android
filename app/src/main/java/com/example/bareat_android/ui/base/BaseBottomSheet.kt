package com.example.bareat_android.ui.base

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.viewbinding.ViewBinding
import com.deishelon.roundedbottomsheet.RoundedBottomSheetDialogFragment
import com.example.bareat_android.R
import com.example.bareat_android.setup.client.Prefs
import com.example.bareat_android.ui.login.MainActivity
import com.google.android.material.snackbar.Snackbar
import org.koin.android.ext.android.inject

abstract class BaseBottomSheet<BINDING : ViewBinding> : RoundedBottomSheetDialogFragment() {

    protected lateinit var binding: BINDING
    protected val prefs by inject<Prefs>()
    protected val navController: NavController? by lazy { activity?.findNavController(R.id.fragment) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return initializeBinding().root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    abstract fun initializeBinding(): BINDING

    abstract fun initView()

    protected fun homeActivity() = (activity as MainActivity)

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

    protected fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}