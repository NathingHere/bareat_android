package com.example.domain.client

import com.example.data.Dish
import com.example.data.Either
import com.example.data.Restaurant
import com.example.data.ReviewRestaurant

interface BareatClient {

    suspend fun getRestaurantList(isMock: Boolean = false): Either<String, List<Restaurant>>

    suspend fun getDishList(isMock: Boolean = false, id: Int): Either<String, List<Dish>>

    suspend fun getCommentList(isMock: Boolean = false, id: Int): Either<String, List<ReviewRestaurant>>

}