package com.example.domain.repository

import com.example.data.DataBook
import com.example.data.Either
import com.example.data.ReviewDish
import com.example.data.ReviewRestaurant
import com.example.data.tosend.RateDishBody
import com.example.data.tosend.RateRestaurantBody
import com.example.domain.client.BareatClient

class ProfileRepository(
    private val bareatClient: BareatClient
) {

    suspend fun getBookList(id: Int): Either<String, List<DataBook>> = bareatClient.getBookList(id)

    suspend fun getUserProductReviews(userId: Int): Either<String, List<ReviewDish>> = bareatClient.getUserProductReviews(userId)

    suspend fun getUserRestaurantReviews(userId: Int): Either<String, List<ReviewRestaurant>> = bareatClient.getUserRestaurantReviews(userId)

    suspend fun editProductReview(reviewId: Int, rateDishBody: RateDishBody): Either<String, Any> = bareatClient.editProductReview(reviewId, rateDishBody)

    suspend fun editRestaurantReview(reviewId: Int, rateRestaurantBody: RateRestaurantBody): Either<String, Any> = bareatClient.editRestaurantReview(reviewId, rateRestaurantBody)

    suspend fun deleteProductReview(bookId: Int): Either<String, Any> = bareatClient.deleteProductReview(bookId)

    suspend fun deleteRestaurantReview(bookId: Int): Either<String, Any> = bareatClient.deleteRestaurantReview(bookId)
}