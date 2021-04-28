package com.example.domain.usecase.restaurant

import com.example.data.Either
import com.example.data.Restaurant
import com.example.domain.provider.RestaurantProvider
import com.example.domain.usecase.base.BaseUseCase

class GetRestaurantListUseCase(
    private val restaurantProvider: RestaurantProvider
) : BaseUseCase<Either<String, List<Restaurant>>> {

    override suspend fun execute(): Either<String, List<Restaurant>> = restaurantProvider.provideRestaurantList()

}