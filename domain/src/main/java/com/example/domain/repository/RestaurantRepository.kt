package com.example.domain.repository

import com.example.data.Either
import com.example.data.Image
import com.example.data.Restaurant
import com.example.domain.client.BareatClient
import com.example.domain.client.MockClient

class RestaurantRepository(
    private val bareatClient: BareatClient
) {

    suspend fun getRestaurantList() : Either<String, List<Restaurant>> = bareatClient.getRestaurantList(false)

    suspend fun getImageList(id: Int): Either<String, List<Image>> = bareatClient.getImageList(id)

}