package com.example.domain.provider

import com.example.data.Either
import com.example.data.torecieve.LoginResponse
import com.example.data.torecieve.RegisterResponse
import com.example.data.tosend.LoginBody
import com.example.data.tosend.RegisterBody
import com.example.domain.repository.OnBoardingRepository

class OnBoardingProvider(
    private val onBoardingRepository: OnBoardingRepository
) {

    suspend fun doRegister(registerBody: RegisterBody): Either<String, RegisterResponse> = onBoardingRepository.doRegister(registerBody)

    suspend fun doLogin(loginBody: LoginBody): Either<String, LoginResponse> = onBoardingRepository.doLogin(loginBody)

}