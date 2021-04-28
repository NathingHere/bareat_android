package com.example.domain.usecase.dish

import com.example.data.Dish
import com.example.data.Either
import com.example.domain.provider.DishProvider
import com.example.domain.usecase.base.BaseUseCaseWithParams

class GetDishListUseCase(
        private val dishProvider: DishProvider
) : BaseUseCaseWithParams<Either<String, List<Dish>>, Int>{

    override suspend fun execute(params: Int): Either<String, List<Dish>> = dishProvider.provideDishList(params)

}