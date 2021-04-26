package com.example.bareat_android.ui.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bareat_android.BuildConfig
import com.example.bareat_android.R
import com.example.bareat_android.databinding.FragmentLoginBinding
import com.example.bareat_android.databinding.FragmentRegisterBinding
import com.example.bareat_android.setup.extensions.isEmail
import com.example.bareat_android.setup.extensions.isValidPassword
import com.example.bareat_android.ui.base.BaseFragment
import com.example.bareat_android.ui.login.LoginFragmentDirections.Companion.routeToOnboarding
import com.example.bareat_android.ui.onboarding.OnBoardingFragmentDirections
import com.example.bareat_android.ui.register.RegisterFragmentDirections.Companion.routeToFinishRegister

class RegisterFragment : BaseFragment<FragmentRegisterBinding>() {

    override fun initializeBinding(): FragmentRegisterBinding {
        binding = FragmentRegisterBinding.inflate(layoutInflater)
        return binding
    }

    override fun initView() {
        with (binding) {

            email.addTextChangedListener(addTextWatcherEmail(inputEmail))

            password.addTextChangedListener(textWatcherPass(inputPassword))

            btnNext.setOnClickListener {
                onRegisterPressed()
            }

            btnBack.setOnClickListener {
                navController?.navigate(routeToOnboarding())
            }
        }
    }

    override fun setToolbar() {

    }

    private fun onRegisterPressed() {
        if (checkInputs()) navController?.navigate(routeToFinishRegister())
    }

    private fun checkInputs(): Boolean {
        with(binding) {
            return if (email.text.toString().isEmail() && email.text.toString().isNotEmpty() && repeat.text.toString() == password.text.toString() && password.text.toString().isNotEmpty() && password.text.toString().length >= 6 && checkboxTerms.isChecked
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
                } else if (!checkboxTerms.isChecked){
                    showError(getString(R.string.error_tyc), constraintContainer)
                } else if (repeat.text.toString() != password.text.toString()) {
                    showError (getString(R.string.error_pass_not_same), constraintContainer)
                    inputRepeat.isErrorEnabled = true
                    inputRepeat.error = getString(R.string.error_pass_not_same)
                }
                false
            }
        }
    }

}