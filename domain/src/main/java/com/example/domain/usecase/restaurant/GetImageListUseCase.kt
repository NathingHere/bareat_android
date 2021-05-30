package com.example.domain.usecase.restaurant

import com.example.data.Dish
import com.example.data.Either
import com.example.data.Image
import com.example.domain.provider.RestaurantProvider
import com.example.domain.usecase.base.BaseUseCaseWithParams

class GetImageListUseCase(
    private val restaurantProvider: RestaurantProvider
) : BaseUseCaseWithParams<Either<String, List<Image>>, Int> {

    override suspend fun execute(params: Int): Either<String, List<Image>> = restaurantProvider.getImageList(params)
}