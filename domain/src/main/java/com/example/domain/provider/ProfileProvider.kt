package com.example.domain.provider

import com.example.data.DataBook
import com.example.data.Either
import com.example.data.ReviewDish
import com.example.data.ReviewRestaurant
import com.example.data.tosend.RateDishBody
import com.example.data.tosend.RateRestaurantBody
import com.example.domain.repository.ProfileRepository

class ProfileProvider(
    private val profileRepository: ProfileRepository
) {

    suspend fun getBookList(id: Int): Either<String, List<DataBook>> = profileRepository.getBookList(id)

    suspend fun getUserProductReviews(userId: Int): Either<String, List<ReviewDish>> = profileRepository.getUserProductReviews(userId)

    suspend fun getUserRestaurantReviews(userId: Int): Either<String, List<ReviewRestaurant>> = profileRepository.getUserRestaurantReviews(userId)

    suspend fun editProductReview(reviewId: Int, rateDishBody: RateDishBody): Either<String, Any> = profileRepository.editProductReview(reviewId, rateDishBody)

    suspend fun editRestaurantReview(reviewId: Int, rateRestaurantBody: RateRestaurantBody): Either<String, Any> = profileRepository.editRestaurantReview(reviewId, rateRestaurantBody)

    suspend fun deleteProductReview(bookId: Int): Either<String, Any> = profileRepository.deleteProductReview(bookId)

    suspend fun deleteRestaurantReview(bookId: Int): Either<String, Any> = profileRepository.deleteRestaurantReview(bookId)
}