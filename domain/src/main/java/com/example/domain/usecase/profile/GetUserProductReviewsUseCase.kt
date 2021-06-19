package com.example.domain.usecase.profile

import com.example.data.Dish
import com.example.data.Either
import com.example.data.ReviewDish
import com.example.data.ReviewRestaurant
import com.example.domain.provider.DishProvider
import com.example.domain.provider.ProfileProvider
import com.example.domain.usecase.base.BaseUseCaseWithParams

class GetUserProductReviewsUseCase(
    private val profileProvider: ProfileProvider
) : BaseUseCaseWithParams<Either<String, List<ReviewDish>>, Int> {
    override suspend fun execute(params: Int): Either<String, List<ReviewDish>> = profileProvider.getUserProductReviews(params)
}