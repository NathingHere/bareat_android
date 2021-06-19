package com.example.domain.usecase.profile

import com.example.data.Either
import com.example.data.ReviewDish
import com.example.data.tosend.RateDishBody
import com.example.data.tosend.RateRestaurantBody
import com.example.domain.provider.ProfileProvider
import com.example.domain.usecase.base.BaseUseCaseWithParams

class EditRestaurantReviewUseCase(
    private val profileProvider: ProfileProvider
) : BaseUseCaseWithParams<Either<String, Any>, ParamsEditRestaurant> {
    override suspend fun execute(params: ParamsEditRestaurant): Either<String, Any> = profileProvider.editRestaurantReview(params.reviewId, params.rateRestaurantBody)

}

data class ParamsEditRestaurant (
    var reviewId: Int,
    var rateRestaurantBody: RateRestaurantBody
)