package com.example.domain.provider

import com.example.data.Dish
import com.example.data.Either
import com.example.domain.repository.DishRepository
import com.example.domain.repository.RestaurantRepository

class DishProvider(
        private val dishRepository: DishRepository
) {

    suspend fun provideDishList(id: Int): Either<String, List<Dish>> = dishRepository.getDishList(id)

}