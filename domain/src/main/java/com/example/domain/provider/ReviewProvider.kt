package com.example.domain.provider

import com.example.data.Either
import com.example.data.Review
import com.example.domain.repository.ReviewRepository

class ReviewProvider(
        private val reviewRepository: ReviewRepository
) {

    suspend fun provideReviewList(id: Int): Either<String, List<Review>> = reviewRepository.getReviewList(id)

}