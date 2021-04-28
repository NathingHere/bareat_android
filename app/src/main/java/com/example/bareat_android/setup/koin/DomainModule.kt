package com.example.bareat_android.setup.koin

import com.example.domain.provider.DishProvider
import com.example.domain.provider.RestaurantProvider
import com.example.domain.provider.ReviewProvider
import com.example.domain.repository.DishRepository
import com.example.domain.repository.RestaurantRepository
import com.example.domain.repository.ReviewRepository
import org.koin.dsl.module

val domainModule = module {

    //RESTAURANT

    single { RestaurantProvider(get()) }
    single { RestaurantRepository(get()) }

    //DISH
    single { DishProvider(get()) }
    single { DishRepository(get()) }

    //REVIEW
    single { ReviewProvider(get()) }
    single { ReviewRepository(get()) }

}