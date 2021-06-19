package com.example.domain.usecase.profile

import com.example.data.Either
import com.example.data.ReviewDish
import com.example.data.ReviewRestaurant
import com.example.domain.provider.ProfileProvider
import com.example.domain.usecase.base.BaseUseCaseWithParams

class GetUserRestaurantReviewsUseCase(
    private val profileProvider: ProfileProvider
) : BaseUseCaseWithParams<Either<String, List<ReviewRestaurant>>, Int> {
    override suspend fun execute(params: Int): Either<String, List<ReviewRestaurant>> = profileProvider.getUserRestaurantReviews(params)
}