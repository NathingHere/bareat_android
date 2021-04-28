package com.example.bareat_android.setup.koin

import com.example.domain.usecase.dish.GetDishListUseCase
import com.example.domain.usecase.restaurant.GetRestaurantListUseCase
import com.example.domain.usecase.review.GetReviewListUseCase
import org.koin.dsl.module

val useCaseModule = module {

    //RESTAURANT
    factory { GetRestaurantListUseCase(get()) }

    //DISH
    factory { GetDishListUseCase(get()) }

    //REVIEW
    factory { GetReviewListUseCase(get()) }

}