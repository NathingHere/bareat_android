package com.example.domain.repository

import com.example.data.Either
import com.example.data.Image
import com.example.data.Restaurant
import com.example.data.torecieve.BookResponse
import com.example.data.torecieve.RateRestaurantResponse
import com.example.data.tosend.BookBody
import com.example.data.tosend.RateRestaurantBody
import com.example.domain.client.BareatClient
import com.example.domain.client.MockClient

class RestaurantRepository(
    private val bareatClient: BareatClient
) {

    suspend fun getRestaurantList() : Either<String, List<Restaurant>> = bareatClient.getRestaurantList(false)

    suspend fun getImageList(id: Int): Either<String, List<Image>> = bareatClient.getImageList(id)

    suspend fun rateRestaurant(userId: Int, restaurantId: Int, rateRestaurantBody: RateRestaurantBody): Either<String, RateRestaurantResponse> = bareatClient.rateRestaurant(userId, restaurantId, rateRestaurantBody)

    suspend fun bookRestaurant(userId: Int, restaurantId: Int, bookBody: BookBody): Either<String, BookResponse> = bareatClient.bookRestaurant(userId, restaurantId, bookBody)

    suspend fun deleteBook(userId: Int, bookId: Int): Either<String, Any> = bareatClient.deleteBook(userId, bookId)

}