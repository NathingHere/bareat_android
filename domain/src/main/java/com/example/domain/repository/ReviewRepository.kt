package com.example.domain.repository

import com.example.data.Either
import com.example.data.Review
import com.example.domain.client.BareatClient

class ReviewRepository(
        private val bareatClient: BareatClient
) {

    suspend fun getReviewList(id: Int) : Either<String, List<Review>> = bareatClient.getCommentList(true, id)

}