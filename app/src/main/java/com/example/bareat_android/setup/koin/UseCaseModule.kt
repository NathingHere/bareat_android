package com.example.bareat_android.setup.koin

import com.example.domain.usecase.dish.GetCommentListDishUseCase
import com.example.domain.usecase.dish.GetDishListUseCase
import com.example.domain.usecase.dish.RateDishUseCase
import com.example.domain.usecase.onboarding.DoLoginUseCase
import com.example.domain.usecase.onboarding.DoRegisterUseCase
import com.example.domain.usecase.profile.GetBookListUseCase
import com.example.domain.usecase.restaurant.*
import com.example.domain.usecase.review.GetReviewListUseCase
import org.koin.dsl.module

val useCaseModule = module {

    //ONBOARDING
    factory { DoRegisterUseCase(get()) }
    factory { DoLoginUseCase(get()) }

    //RESTAURANT
    factory { GetRestaurantListUseCase(get()) }
    factory { GetImageListUseCase(get()) }
    factory { RateRestaurantUseCase(get()) }
    factory { BookRestaurantUseCase(get()) }
    factory { DeleteBookUseCase(get()) }

    //DISH
    factory { GetDishListUseCase(get()) }
    factory { RateDishUseCase(get()) }
    factory { GetCommentListDishUseCase(get()) }

    //REVIEW
    factory { GetReviewListUseCase(get()) }

    //PROFILE
    factory { GetBookListUseCase(get()) }

}