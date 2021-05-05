package com.example.domain.client

import com.example.data.Dish
import com.example.data.Restaurant
import com.example.data.ReviewRestaurant
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface BareatService {

    @GET("/api/restaurant")
    suspend fun getRestaurantList(): Response<List<Restaurant>>

    @GET("/api/dishes")
    suspend fun getDishList(@Path("id") id: Int): Response<List<Dish>>

    @GET("/api/reviews")
    suspend fun getCommentList(@Path("id") id: Int): Response<List<ReviewRestaurant>>


}