package com.example.bareat_android.ui.login

import com.example.bareat_android.R
import com.example.bareat_android.databinding.FragmentLoginBinding
import com.example.bareat_android.setup.extensions.isEmail
import com.example.bareat_android.setup.extensions.isValidPassword
import com.example.bareat_android.setup.extensions.launchActivity
import com.example.bareat_android.ui.base.BaseFragment
import com.example.bareat_android.ui.base.BaseViewModel
import com.example.bareat_android.ui.login.LoginFragmentDirections.Companion.routeToOnboarding
import com.example.data.tosend.LoginBody
import org.koin.androidx.viewmodel.ext.android.viewModel


class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    private val loginViewModel by viewModel<LoginViewModel>()

    override fun initializeBinding(): FragmentLoginBinding {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding
    }

    override fun initView() {
        loginViewModel.init()

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

            loginViewModel.loginLiveData.observe(viewLifecycleOwner) { manageLoginScreenState(it) }
        }

    }

    private fun manageLoginScreenState(state: BaseViewModel.ScreenState<LoginViewModel.LoginState>?) {
        when (state) {
            BaseViewModel.ScreenState.LOADING -> showProgressDialog()
            is BaseViewModel.ScreenState.RenderData -> manageLoginState(state.renderState)
        }
    }

    private fun manageLoginState(state: LoginViewModel.LoginState) {
        hideProgressDialog()
        when (state) {
            is LoginViewModel.LoginState.SUCCESS -> {
                prefs.id = state.loginResponse.dataProfile?.id.toString()
                prefs.email = state.loginResponse.dataProfile?.email
                prefs.name = state.loginResponse.dataProfile?.name + " " + state.loginResponse.dataProfile?.surname
                prefs.token = state.loginResponse.token
                activity?.launchActivity<MainActivity>(true)
            }
            is LoginViewModel.LoginState.ERROR -> showMessage(if(state.errorMessage == "No estas autorizado" ) "El email o la contraseña son incorrectos" else state.errorMessage, binding.constraintContainer)
        }
    }

    private fun onLoginPressed() {
        if (checkInputs()) {
            with(binding) {
                loginViewModel.doLogin(LoginBody(email.text.toString(), password.text.toString()))
            }
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