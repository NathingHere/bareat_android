package com.example.domain.provider

import com.example.data.Either
import com.example.data.Restaurant
import com.example.domain.repository.RestaurantRepository

class RestaurantProvider(
    private val restaurantRepository: RestaurantRepository
) {

    suspend fun provideRestaurantList(): Either<String, List<Restaurant>> = restaurantRepository.getRestaurantList()

}