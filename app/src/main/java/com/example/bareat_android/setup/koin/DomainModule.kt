package com.example.bareat_android.setup.koin

import com.example.domain.provider.*
import com.example.domain.repository.*
import org.koin.dsl.module

val domainModule = module {

    //ONBOARDING

    single { OnBoardingProvider(get()) }
    single { OnBoardingRepository(get()) }

    //RESTAURANT

    single { RestaurantProvider(get()) }
    single { RestaurantRepository(get()) }

    //DISH
    single { DishProvider(get()) }
    single { DishRepository(get()) }

    //REVIEW
    single { ReviewProvider(get()) }
    single { ReviewRepository(get()) }

    //PROFILE
    single { ProfileProvider(get()) }
    single { ProfileRepository(get()) }

}