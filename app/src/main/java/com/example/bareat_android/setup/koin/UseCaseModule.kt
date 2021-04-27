package com.example.bareat_android.setup.koin

import com.example.domain.usecase.restaurant.GetRestaurantRatedListUseCase
import org.koin.dsl.module

val useCaseModule = module {

    //RESTAURANT
    factory { GetRestaurantRatedListUseCase(get()) }

}