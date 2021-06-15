package com.example.domain.provider

import com.example.data.Dish
import com.example.data.Either
import com.example.data.ReviewDish
import com.example.data.torecieve.RateDishResponse
import com.example.data.tosend.RateDishBody
import com.example.domain.repository.DishRepository
import com.example.domain.repository.RestaurantRepository

class DishProvider(
        private val dishRepository: DishRepository
) {

    suspend fun provideDishList(id: Int): Either<String, List<Dish>> = dishRepository.getDishList(id)

    suspend fun rateDish(userId: Int, dishId: Int, rateDishBody: RateDishBody): Either<String, RateDishResponse> = dishRepository.rateDish(userId, dishId, rateDishBody)

    suspend fun getCommentListDish(dishId: Int): Either<String, List<ReviewDish>> = dishRepository.getCommentListDish(dishId)

}