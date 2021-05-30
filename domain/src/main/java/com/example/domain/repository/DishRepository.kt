package com.example.domain.repository

import com.example.data.Dish
import com.example.data.Either
import com.example.domain.client.BareatClient

class DishRepository(
        private val bareatClient: BareatClient
) {

    suspend fun getDishList(id: Int) : Either<String, List<Dish>> = bareatClient.getDishList(false, id)

}