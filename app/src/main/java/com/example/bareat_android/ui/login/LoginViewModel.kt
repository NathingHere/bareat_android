package com.example.bareat_android.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.bareat_android.ui.base.BaseViewModel
import com.example.data.fold
import com.example.data.torecieve.LoginResponse
import com.example.data.torecieve.RegisterResponse
import com.example.data.tosend.LoginBody
import com.example.data.tosend.RegisterBody
import com.example.domain.usecase.onboarding.DoLoginUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel (private val doLoginUseCase: DoLoginUseCase) : BaseViewModel() {

        sealed class LoginState {
                data class SUCCESS(val loginResponse: LoginResponse) : LoginState()
                data class ERROR(val errorMessage: String) : LoginState()
        }

        private val _loginMutableData = MutableLiveData<ScreenState<LoginState>>()
        val loginLiveData: LiveData<ScreenState<LoginState>>
                get() = _loginMutableData

        fun doLogin(loginBody: LoginBody) {
                updateUI(ScreenState.LOADING)
                uiScope.launch {
                        val result = uiScope.async { withContext(ioContext) { doLoginUseCase.execute(loginBody) } }

                        result.await().fold(
                                { updateUI(ScreenState.RenderData(LoginState.ERROR(it))) },
                                { updateUI(ScreenState.RenderData(LoginState.SUCCESS(it))) }
                        )
                }
        }

        private fun updateUI(state:ScreenState<LoginState>) {
                _loginMutableData.postValue(state)
        }
}