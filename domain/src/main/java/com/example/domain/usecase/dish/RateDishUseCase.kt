package com.example.domain.usecase.dish

import com.example.data.Either
import com.example.data.torecieve.BookResponse
import com.example.data.torecieve.RateDishResponse
import com.example.data.tosend.BookBody
import com.example.data.tosend.RateDishBody
import com.example.domain.provider.DishProvider
import com.example.domain.provider.RestaurantProvider
import com.example.domain.usecase.base.BaseUseCaseWithParams
import com.example.domain.usecase.restaurant.ParamsBookRestaurant

class RateDishUseCase(
    private val dishProvider: DishProvider
) : BaseUseCaseWithParams<Either<String, RateDishResponse>, ParamsRateDish> {
    override suspend fun execute(params: ParamsRateDish): Either<String, RateDishResponse> = dishProvider.rateDish(params.userId, params.dishId, params.rateDishBody)
}

data class ParamsRateDish (
    var userId: Int,
    var dishId: Int,
    var rateDishBody: RateDishBody
)