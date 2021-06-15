package com.example.bareat_android.ui.register

import android.app.DatePickerDialog
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bareat_android.BuildConfig
import com.example.bareat_android.R
import com.example.bareat_android.databinding.FragmentLoginBinding
import com.example.bareat_android.databinding.FragmentRegisterBinding
import com.example.bareat_android.setup.extensions.addTenths
import com.example.bareat_android.setup.extensions.isEmail
import com.example.bareat_android.setup.extensions.isValidPassword
import com.example.bareat_android.setup.extensions.launchActivity
import com.example.bareat_android.ui.base.BaseFragment
import com.example.bareat_android.ui.base.BaseViewModel
import com.example.bareat_android.ui.login.MainActivity
import com.example.bareat_android.ui.onboarding.OnBoardingFragmentDirections
import com.example.bareat_android.ui.restaurant.RestaurantViewModel
import com.example.data.tosend.RegisterBody
import kotlinx.android.synthetic.main.fragment_register.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class RegisterFragment : BaseFragment<FragmentRegisterBinding>() {

    private val registerViewModel by viewModel<RegisterViewModel>()

    private var birthdayToSend = ""
    var yearOlder18 = 0
    var yearSelected = 0

    override fun initializeBinding(): FragmentRegisterBinding {
        binding = FragmentRegisterBinding.inflate(layoutInflater)
        return binding
    }

    override fun initView() {
        registerViewModel.init()

        with (binding) {

            email.addTextChangedListener(addTextWatcherEmail(inputEmail))

            password.addTextChangedListener(textWatcherPass(inputPassword))

            btnNext.setOnClickListener {
                onNextPressed()
            }

            btnBack.setOnClickListener {
                navController?.navigate(RegisterFragmentDirections.routeToOnboarding())
            }

            inputBirthdayLayout.setOnClickListener { datePickerDialog() }

            registerViewModel.registerLiveData.observe(viewLifecycleOwner) { manageRegisterScreenState(it) }

        }
    }

    private fun manageRegisterScreenState(state: BaseViewModel.ScreenState<RegisterViewModel.RegisterState>?) {
        when (state) {
            BaseViewModel.ScreenState.LOADING -> showProgressDialog()
            is BaseViewModel.ScreenState.RenderData -> manageRegisterState(state.renderState)
        }
    }

    private fun manageRegisterState(state: RegisterViewModel.RegisterState) {
        hideProgressDialog()
        when (state) {
            is RegisterViewModel.RegisterState.SUCCESS -> {
                showMessage("Registrado con éxito", binding.constraintContainer)
                navController?.navigate(RegisterFragmentDirections.routeToOnboarding())
            }
            is RegisterViewModel.RegisterState.ERROR -> showMessage(state.errorMessage, binding.constraintContainer)
        }
    }

    private fun onNextPressed() {
        if(checkInputs() && checkPhoneNumber()) {
            with(binding) {
                registerViewModel.doRegister(RegisterBody(email.text.toString(), password.text.toString(), name.text.toString(), surname.text.toString(), if (phone.text?.isNotEmpty() == true) phone.text.toString().toInt() else null, if(inputBirthdayText.text.isNotEmpty()) birthdayToSend else null))
            }
        }
    }

    private fun checkPhoneNumber(): Boolean {
        with(binding) {
            return if (phone.text?.toString()?.isEmpty() == true ||
                (phone.text?.toString()?.isEmpty() == false && phone.text?.toString()?.length!! == 9 && phone.text?.toString()
                    ?.isEmpty() == false)
            ) {
                true
            } else {
                showError(getString(R.string.error_phone_not_valid), binding.constraintContainer)
                false
            }
        }
    }

    private fun datePickerDialog() {
        val calendar = Calendar.getInstance()
        val thisYear = calendar.get(Calendar.YEAR)
        var bookYear = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        var bookMonth = calendar.get(Calendar.MONTH)
        val today = calendar.get(Calendar.DAY_OF_MONTH)
        var bookDay = calendar.get(Calendar.DAY_OF_MONTH)

        val dpd = context?.let { context ->
            DatePickerDialog(
                context, R.style.datepicker,
                { _, year, month, dayOfMonth ->
                    val sdf = SimpleDateFormat("dd/MM/yyyy")
                    val date = String.format(
                        "%s/%s/%s",
                        dayOfMonth.toString().addTenths(),
                        (month + 1).toString().addTenths(),
                        year.toString().addTenths()
                    )
                    val d = sdf.parse(date);
                    inputBirthdayText.text = DateFormat.getDateInstance(DateFormat.LONG).format(d)
                    birthdayToSend = String.format(
                        "%s-%s-%s",
                        year.toString().addTenths(),
                        (month + 1).toString().addTenths(),
                        dayOfMonth.toString().addTenths()
                    )

                    bookYear = year
                    bookMonth = month + 1
                    bookDay = dayOfMonth
                    yearOlder18 = thisYear - 18
                    yearSelected = year
                },
                thisYear,
                month,
                today
            )
        }
        dpd?.show()
        dpd?.datePicker?.maxDate = System.currentTimeMillis() - 568025136000
        dpd?.getButton(DatePickerDialog.BUTTON_NEGATIVE)
            ?.setTextColor(resources.getColor(R.color.colorPrimary, resources.newTheme()))
        dpd?.getButton(DatePickerDialog.BUTTON_POSITIVE)
            ?.setTextColor(resources.getColor(R.color.colorPrimary, resources.newTheme()))
    }

    private fun checkInputs(): Boolean {
        with(binding) {
            return if (email.text.toString().isEmail() && email.text.toString().isNotEmpty() && repeat.text.toString() == password.text.toString() && password.text.toString().isNotEmpty() && password.text.toString().length >= 6 && checkboxTerms.isChecked && name.text.toString().isNotEmpty() && surname.text.toString().isNotEmpty()
            ) {
                true
            } else {
                if (email.text.toString().isEmpty() || password.text.toString().isEmpty() || name.text.toString().isEmpty() || surname.text.toString().isEmpty()) {
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

    override fun setToolbar() {

    }

}