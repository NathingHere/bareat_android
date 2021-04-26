package com.example.bareat_android.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bareat_android.R
import com.example.bareat_android.databinding.FragmentLoginBinding
import com.example.bareat_android.setup.extensions.isEmail
import com.example.bareat_android.setup.extensions.isValidPassword
import com.example.bareat_android.setup.extensions.launchActivity
import com.example.bareat_android.ui.base.BaseFragment
import com.example.bareat_android.ui.login.LoginFragmentDirections.Companion.routeToOnboarding


class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    override fun initializeBinding(): FragmentLoginBinding {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding
    }

    override fun initView() {

        with(binding) {

            email.addTextChangedListener(addTextWatcherEmail(inputEmail))

            password.addTextChangedListener(textWatcherPass(inputPassword))

            btnNext.setOnClickListener {
                onLoginPressed()
            }

            tvForgot.setOnClickListener {

            }

            btnBack.setOnClickListener {
                navController?.navigate(routeToOnboarding())
            }
        }

    }

    private fun onLoginPressed() {
        if (checkInputs()) {
            routeToHome()
        }
    }

    private fun checkInputs(): Boolean {
        with(binding) {
            return if (email.text.toString().isEmail() && email.text.toString().isNotEmpty() && password.text.toString().isNotEmpty() && password.text.toString().length >= 6
            ) {
                true
            } else {
                if (email.text.toString().isEmpty() && password.text.toString().isEmpty()) {
                    showError(getString(R.string.error_fields_empty), constraintContainer)
                } else if (!password.text.toString().isValidPassword()) {
                    showError(getString(R.string.error_pass_not_valid), constraintContainer)
                } else if (!email.text.toString().isEmail() || email.text.toString()
                                .isEmpty()
                ) {
                    showError(getString(R.string.error_email), constraintContainer)
                }
                false
            }
        }
    }

    override fun setToolbar() {

    }

    private fun routeToHome() {
        activity?.launchActivity<MainActivity>(true)
    }

}