package com.example.domain.repository

import com.example.data.Either
import com.example.data.torecieve.RegisterResponse
import com.example.data.tosend.RegisterBody
import com.example.domain.client.BareatClient

class OnBoardingRepository(
    private val bareatClient: BareatClient
) {

    suspend fun doRegister(registerBody: RegisterBody): Either<String, RegisterResponse> = bareatClient.doRegister(registerBody)

}