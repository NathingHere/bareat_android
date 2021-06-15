package com.example.domain.provider

import com.example.data.Either
import com.example.data.Image
import com.example.data.Restaurant
import com.example.data.torecieve.BookResponse
import com.example.data.torecieve.RateRestaurantResponse
import com.example.data.tosend.BookBody
import com.example.data.tosend.RateRestaurantBody
import com.example.domain.repository.RestaurantRepository

class RestaurantProvider(
    private val restaurantRepository: RestaurantRepository
) {

    suspend fun provideRestaurantList(): Either<String, List<Restaurant>> = restaurantRepository.getRestaurantList()

    suspend fun getImageList(id: Int): Either<String, List<Image>> = restaurantRepository.getImageList(id)

    suspend fun rateRestaurant(userId: Int, restaurantId: Int, rateRestaurantBody: RateRestaurantBody): Either<String, RateRestaurantResponse> = restaurantRepository.rateRestaurant(userId, restaurantId, rateRestaurantBody)

    suspend fun bookRestaurant(userId: Int, restaurantId: Int, bookBody: BookBody): Either<String, BookResponse> = restaurantRepository.bookRestaurant(userId, restaurantId, bookBody)

    suspend fun deleteBook(userId: Int, bookId: Int): Either<String, Any> = restaurantRepository.deleteBook(userId, bookId)
}