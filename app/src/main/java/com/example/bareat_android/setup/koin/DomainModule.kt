package com.example.bareat_android.setup.koin

import com.example.domain.provider.RestaurantProvider
import com.example.domain.repository.RestaurantRepository
import org.koin.dsl.module

val domainModule = module {

    //RESTAURANT

    single { RestaurantProvider(get()) }
    single { RestaurantRepository(get()) }

}