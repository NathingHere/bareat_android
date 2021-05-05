package com.example.domain.provider

import com.example.data.Either
import com.example.data.ReviewRestaurant
import com.example.domain.repository.ReviewRepository

class ReviewProvider(
        private val reviewRepository: ReviewRepository
) {

    suspend fun provideReviewList(id: Int): Either<String, List<ReviewRestaurant>> = reviewRepository.getReviewList(id)

}