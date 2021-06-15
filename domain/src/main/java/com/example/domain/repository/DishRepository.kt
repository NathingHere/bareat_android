package com.example.domain.repository

import com.example.data.Dish
import com.example.data.Either
import com.example.data.ReviewDish
import com.example.data.torecieve.RateDishResponse
import com.example.data.tosend.RateDishBody
import com.example.domain.client.BareatClient

class DishRepository(
        private val bareatClient: BareatClient
) {

    suspend fun getDishList(id: Int) : Either<String, List<Dish>> = bareatClient.getDishList(false, id)

    suspend fun rateDish(userId: Int, dishId: Int, rateDishBody: RateDishBody): Either<String, RateDishResponse> = bareatClient.rateDish(userId, dishId, rateDishBody)

    suspend fun getCommentListDish(dishId: Int): Either<String, List<ReviewDish>> = bareatClient.getCommentListDish(dishId)

}