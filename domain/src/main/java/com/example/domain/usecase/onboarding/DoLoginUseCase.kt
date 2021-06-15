package com.example.domain.usecase.onboarding

import com.example.data.Either
import com.example.data.torecieve.LoginResponse
import com.example.data.torecieve.RegisterResponse
import com.example.data.tosend.LoginBody
import com.example.data.tosend.RegisterBody
import com.example.domain.provider.OnBoardingProvider
import com.example.domain.usecase.base.BaseUseCaseWithParams

class DoLoginUseCase(
    private val onBoardingProvider: OnBoardingProvider
) : BaseUseCaseWithParams<Either<String, LoginResponse>, LoginBody> {

    override suspend fun execute(params: LoginBody): Either<String, LoginResponse> = onBoardingProvider.doLogin(params)
}