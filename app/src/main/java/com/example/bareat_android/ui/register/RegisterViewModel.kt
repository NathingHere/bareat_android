package com.example.bareat_android.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.bareat_android.ui.base.BaseViewModel
import com.example.bareat_android.ui.restaurant.RestaurantViewModel
import com.example.data.Dish
import com.example.data.fold
import com.example.data.torecieve.RegisterResponse
import com.example.data.tosend.RegisterBody
import com.example.domain.usecase.onboarding.DoRegisterUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterViewModel(
    private val doRegisterUseCase: DoRegisterUseCase
) : BaseViewModel() {

    sealed class RegisterState {
        data class SUCCESS(val registerResponse: RegisterResponse) : RegisterState()
        data class ERROR(val errorMessage: String) : RegisterState()
    }

    private val _registerMutableData = MutableLiveData<ScreenState<RegisterState>>()
    val registerLiveData: LiveData<ScreenState<RegisterState>>
        get() = _registerMutableData

    fun doRegister(registerBody: RegisterBody) {
        updateUI(ScreenState.LOADING)
        uiScope.launch {
            val result = uiScope.async { withContext(ioContext) { doRegisterUseCase.execute(registerBody) } }

            result.await().fold(
                { updateUI(ScreenState.RenderData(RegisterState.ERROR(it))) },
                { updateUI(ScreenState.RenderData(RegisterState.SUCCESS(it))) }
            )
        }
    }

    private fun updateUI(state:ScreenState<RegisterState>) {
        _registerMutableData.postValue(state)
    }

}