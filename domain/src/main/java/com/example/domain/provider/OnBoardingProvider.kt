package com.example.domain.provider

import com.example.data.Either
import com.example.data.torecieve.RegisterResponse
import com.example.data.tosend.RegisterBody
import com.example.domain.repository.OnBoardingRepository

class OnBoardingProvider(
    private val onBoardingRepository: OnBoardingRepository
) {

    suspend fun doRegister(registerBody: RegisterBody): Either<String, RegisterResponse> = onBoardingRepository.doRegister(registerBody)

}