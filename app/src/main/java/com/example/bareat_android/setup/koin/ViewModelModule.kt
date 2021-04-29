package com.example.bareat_android.setup.koin

import com.example.bareat_android.ui.restaurant.RestaurantViewModel
import com.example.bareat_android.ui.home.HomeViewModel
import com.example.bareat_android.ui.search.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel { HomeViewModel(get()) }
    viewModel { RestaurantViewModel(get(), get()) }
    viewModel { SearchViewModel(get()) }

}