package com.example.domain.usecase.restaurant

import com.example.data.Either
import com.example.data.torecieve.BookResponse
import com.example.data.tosend.BookBody
import com.example.domain.provider.RestaurantProvider
import com.example.domain.usecase.base.BaseUseCaseWithParams

class BookRestaurantUseCase(
    private val restaurantProvider: RestaurantProvider
) : BaseUseCaseWithParams<Either<String, BookResponse>, ParamsBookRestaurant> {
    override suspend fun execute(params: ParamsBookRestaurant): Either<String, BookResponse> = restaurantProvider.bookRestaurant(params.userId, params.restaurantId, params.bookBody)
}

data class ParamsBookRestaurant (
    var userId: Int,
    var restaurantId: Int,
    var bookBody: BookBody
    )