package com.example.domain.client

import com.example.data.Dish
import com.example.data.Either
import com.example.data.Restaurant
import com.example.data.ReviewRestaurant

interface MockClient {

    suspend fun getRestaurantList(): Either<String, List<Restaurant>>

    suspend fun getDishList(id: Int): Either<String, List<Dish>>

    suspend fun getCommentList(id: Int): Either<String, List<ReviewRestaurant>>
}