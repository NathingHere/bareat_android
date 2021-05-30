package com.example.bareat_android.setup.koin

import com.example.domain.usecase.dish.GetDishListUseCase
import com.example.domain.usecase.onboarding.DoRegisterUseCase
import com.example.domain.usecase.restaurant.GetImageListUseCase
import com.example.domain.usecase.restaurant.GetRestaurantListUseCase
import com.example.domain.usecase.review.GetReviewListUseCase
import org.koin.dsl.module

val useCaseModule = module {

    //ONBOARDING
    factory { DoRegisterUseCase(get()) }

    //RESTAURANT
    factory { GetRestaurantListUseCase(get()) }
    factory { GetImageListUseCase(get()) }

    //DISH
    factory { GetDishListUseCase(get()) }

    //REVIEW
    factory { GetReviewListUseCase(get()) }

}