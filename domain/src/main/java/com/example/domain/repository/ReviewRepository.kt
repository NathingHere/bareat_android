package com.example.domain.repository

import com.example.data.Either
import com.example.data.ReviewRestaurant
import com.example.domain.client.BareatClient

class ReviewRepository(
        private val bareatClient: BareatClient
) {

    suspend fun getReviewList(id: Int) : Either<String, List<ReviewRestaurant>> = bareatClient.getCommentList(true, id)

}