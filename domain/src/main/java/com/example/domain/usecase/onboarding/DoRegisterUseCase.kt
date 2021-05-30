package com.example.domain.usecase.onboarding

import com.example.data.Either
import com.example.data.Image
import com.example.data.torecieve.RegisterResponse
import com.example.data.tosend.RegisterBody
import com.example.domain.provider.OnBoardingProvider
import com.example.domain.provider.RestaurantProvider
import com.example.domain.usecase.base.BaseUseCaseWithParams

class DoRegisterUseCase(
    private val onBoardingProvider: OnBoardingProvider
) : BaseUseCaseWithParams<Either<String, RegisterResponse>, RegisterBody> {

    override suspend fun execute(params: RegisterBody): Either<String, RegisterResponse> = onBoardingProvider.doRegister(params)

}