package com.example.domain.usecase.restaurant

import com.example.data.Either
import com.example.data.torecieve.BookResponse
import com.example.data.torecieve.RateRestaurantResponse
import com.example.data.tosend.RateDishBody
import com.example.data.tosend.RateRestaurantBody
import com.example.domain.provider.RestaurantProvider
import com.example.domain.usecase.base.BaseUseCaseWithParams

class RateRestaurantUseCase(
    private val restaurantProvider: RestaurantProvider
) : BaseUseCaseWithParams<Either<String, RateRestaurantResponse>, ParamsRateRestaurant> {
    override suspend fun execute(params: ParamsRateRestaurant): Either<String, RateRestaurantResponse> = restaurantProvider.rateRestaurant(params.userId, params.restaurantId, params.rateRestaurantBody)
}

data class ParamsRateRestaurant (
    var userId: Int,
    var restaurantId: Int,
    var rateRestaurantBody: RateRestaurantBody
)