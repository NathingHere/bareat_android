package com.example.bareat_android.ui.base

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.viewbinding.ViewBinding
import com.example.bareat_android.R
import com.example.bareat_android.setup.client.Prefs
import com.example.bareat_android.setup.extensions.isEmail
import com.example.bareat_android.setup.extensions.isValidPassword
import com.example.bareat_android.ui.login.MainActivity
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import org.koin.android.ext.android.inject

abstract class BaseFragment<BINDING : ViewBinding> : Fragment() {

    protected lateinit var binding: BINDING
    protected val prefs by inject<Prefs>()
    protected val navController: NavController? by lazy { activity?.findNavController(R.id.fragment) }
    private var dialog: Dialog? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return initializeBinding().root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        setToolbar()
    }

    abstract fun initializeBinding(): BINDING

    abstract fun initView()

    abstract fun setToolbar()

    protected fun homeActivity() = (activity as MainActivity)

    protected fun provideToolbar() = homeActivity().provideToolbar()

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

    fun showProgressDialog() {
        context?.let {
            dialog = Dialog(it).apply {
                requestWindowFeature(Window.FEATURE_NO_TITLE)
                setCancelable(false)
                setContentView(R.layout.dialog_progress)
                window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                show()
            }
        }
    }

    fun addTextWatcherEmail(inputEmailLayout: TextInputLayout): TextWatcher {
        return object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (s.toString().isEmail()) {
                    inputEmailLayout.isErrorEnabled = false
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                if (s.toString().isEmail()) {
                    inputEmailLayout.isErrorEnabled = false
                }
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.toString().isEmail()) {
                    inputEmailLayout.isErrorEnabled = true
                    inputEmailLayout.error = getString(R.string.error_email)
                }
            }
        }
    }

    fun textWatcherPass(inputPasswordLayout: TextInputLayout): TextWatcher {
        return object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (s.toString().isValidPassword()) {
                    inputPasswordLayout.isErrorEnabled = false
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                if (s.toString().isValidPassword()) {
                    inputPasswordLayout.isErrorEnabled = false
                }
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.toString().isValidPassword()) {
                    inputPasswordLayout.isErrorEnabled = true
                    inputPasswordLayout.error = getString(R.string.error_pass_not_valid)
                }
            }
        }
    }

    fun hideProgressDialog() {
        if (dialog != null) dialog?.dismiss()
    }

}