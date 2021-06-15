package com.example.bareat_android.ui.book

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bareat_android.R
import com.example.bareat_android.databinding.FragmentBookBinding
import com.example.bareat_android.setup.extensions.addTenths
import com.example.bareat_android.setup.extensions.isEmail
import com.example.bareat_android.setup.extensions.isValidPassword
import com.example.bareat_android.ui.base.BaseBottomSheet
import com.example.bareat_android.ui.base.BaseViewModel
import com.example.bareat_android.ui.restaurant.RestaurantViewModel
import com.example.data.tosend.BookBody
import kotlinx.android.synthetic.main.fragment_book.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class BookFragment : BaseBottomSheet<FragmentBookBinding>() {

    private val restaurantViewModel by viewModel<RestaurantViewModel>()

    private var dateToSend = ""
    private var hourToSend = ""

    override fun initializeBinding(): FragmentBookBinding {
        binding = FragmentBookBinding.inflate(layoutInflater)
        return binding
    }

    override fun initView() {
        restaurantViewModel.init()

        restaurantViewModel.bookListData.observe(viewLifecycleOwner) { manageBookScreenState(it) }

        with(binding) {
            inputDateLayout.setOnClickListener {
                datePickerDialog()
            }
            inputHourLayout.setOnClickListener {
                timePickerDialog()
            }
            btnBook.setOnClickListener {
                if (checkInputs()) {
                    prefs.id?.let { it1 -> prefs.restaurantId?.let { it2 -> restaurantViewModel.bookRestaurant(it1.toInt(), it2.toInt(), BookBody(number.text.toString().toInt(), dateToSend, hourToSend)) } }
                }

            }
        }

    }

    private fun manageBookScreenState(state: BaseViewModel.ScreenState<RestaurantViewModel.BookRestaurantState>?) {
        when(state) {
            BaseViewModel.ScreenState.LOADING -> showProgressDialog()
            is BaseViewModel.ScreenState.RenderData -> {
                manageBookState(state.renderState)
            }
        }
    }

    private fun manageBookState(state: RestaurantViewModel.BookRestaurantState) {
        hideProgressDialog()
        when(state){
            is RestaurantViewModel.BookRestaurantState.SUCCESS -> {
                showToast("Mesa reservada con éxito")
                dismiss()
            }
            is RestaurantViewModel.BookRestaurantState.ERROR -> showToast(state.errorMessage)
        }
    }

    private fun checkInputs() : Boolean {
        with(binding) {
            return if ( inputDateText.text.toString().isNotEmpty() && inputHourText.text.toString().isNotEmpty() && number.text.toString().isNotEmpty()
            ) { if (number.text.toString().toInt() != 0 && number.text.toString().toInt() <= 10) {
                true
            }
                else {
                if (number.text.toString().toInt() == 0 || number.text.toString().toInt() > 10) {
                    showToast("El número de comensales no es válido")
                }
                false
            }
            } else {
                if (inputDateText.text.toString().isEmpty()) {
                    showToast("Introduzca una fecha")
                } else if (inputHourText.text.toString().isEmpty()) {
                    showToast("Introduzca una hora")
                } else if(number.text.toString() == "") {
                    showToast("Introduzca el número de comensales")
                }
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
                    inputDateText.text = DateFormat.getDateInstance(DateFormat.LONG).format(d)
                    dateToSend = String.format(
                        "%s-%s-%s",
                        year.toString().addTenths(),
                        (month + 1).toString().addTenths(),
                        dayOfMonth.toString().addTenths()
                    )

                    bookYear = year
                    bookMonth = month + 1
                    bookDay = dayOfMonth
                },
                thisYear,
                month,
                today
            )
        }
        dpd?.show()
        dpd?.datePicker?.minDate = System.currentTimeMillis() + (1000 * 60 * 60 * 24)
        dpd?.getButton(DatePickerDialog.BUTTON_NEGATIVE)
            ?.setTextColor(resources.getColor(R.color.colorPrimary, resources.newTheme()))
        dpd?.getButton(DatePickerDialog.BUTTON_POSITIVE)
            ?.setTextColor(resources.getColor(R.color.colorPrimary, resources.newTheme()))
    }

    private fun timePickerDialog() {
        val cal = Calendar.getInstance()
        val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
            cal.set(Calendar.HOUR_OF_DAY, hour)
            cal.set(Calendar.MINUTE, minute)
            inputHourText.text = SimpleDateFormat("HH:mm").format(cal.time)
            hourToSend = SimpleDateFormat("HH:mm").format(cal.time) + ":00"
        }
        TimePickerDialog(requireContext(), timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()

    }

}