package com.example.domain.client

import com.example.data.*
import com.example.data.torecieve.RegisterResponse
import com.example.data.tosend.RegisterBody

interface BareatClient {

    suspend fun doRegister(registerBody: RegisterBody): Either<String, RegisterResponse>

    suspend fun getRestaurantList(isMock: Boolean = false): Either<String, List<Restaurant>>

    suspend fun getDishList(isMock: Boolean = false, id: Int): Either<String, List<Dish>>

    suspend fun getCommentList(isMock: Boolean = false, id: Int): Either<String, List<ReviewRestaurant>>

    suspend fun getImageList(id: Int): Either<String, List<Image>>

}