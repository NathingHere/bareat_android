package com.example.bareat_android.setup.koin

import com.example.bareat_android.ui.dish.DishViewModel
import com.example.bareat_android.ui.restaurant.RestaurantViewModel
import com.example.bareat_android.ui.home.HomeViewModel
import com.example.bareat_android.ui.login.LoginViewModel
import com.example.bareat_android.ui.profile.ProfileViewModel
import com.example.bareat_android.ui.register.RegisterViewModel
import com.example.bareat_android.ui.search.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel { RegisterViewModel(get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { HomeViewModel(get()) }
    viewModel { RestaurantViewModel(get(), get(), get(), get(), get()) }
    viewModel { DishViewModel(get(), get()) }
    viewModel { SearchViewModel(get()) }
    viewModel { ProfileViewModel(get(), get()) }

}