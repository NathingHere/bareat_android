package com.example.domain.usecase.review

import com.example.data.Either
import com.example.data.Review
import com.example.domain.provider.ReviewProvider
import com.example.domain.usecase.base.BaseUseCaseWithParams

class GetReviewListUseCase(
        private val reviewProvider: ReviewProvider
) : BaseUseCaseWithParams<Either<String, List<Review>>, Int> {

    override suspend fun execute(params: Int): Either<String, List<Review>> = reviewProvider.provideReviewList(params)

}