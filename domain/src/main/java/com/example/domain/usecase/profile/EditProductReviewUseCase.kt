package com.example.domain.usecase.profile

import com.example.data.Either
import com.example.data.tosend.RateDishBody
import com.example.data.tosend.RateRestaurantBody
import com.example.domain.provider.ProfileProvider
import com.example.domain.usecase.base.BaseUseCaseWithParams

class EditProductReviewUseCase(
    private val profileProvider: ProfileProvider
) : BaseUseCaseWithParams<Either<String, Any>, ParamsEditDish> {
    override suspend fun execute(params: ParamsEditDish): Either<String, Any> = profileProvider.editProductReview(params.reviewId, params.rateDishBody)

}

data class ParamsEditDish (
    var reviewId: Int,
    var rateDishBody: RateDishBody
)