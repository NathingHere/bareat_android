package com.example.domain.usecase.dish

import com.example.data.Either
import com.example.data.ReviewDish
import com.example.data.ReviewRestaurant
import com.example.domain.provider.DishProvider
import com.example.domain.provider.ReviewProvider
import com.example.domain.usecase.base.BaseUseCaseWithParams

class GetCommentListDishUseCase(
    private val dishProvider: DishProvider
) : BaseUseCaseWithParams<Either<String, List<ReviewDish>>, Int> {
    override suspend fun execute(params: Int): Either<String, List<ReviewDish>> = dishProvider.getCommentListDish(params)
}