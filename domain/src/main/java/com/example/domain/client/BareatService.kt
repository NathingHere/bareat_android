package com.example.domain.client

import com.example.data.Restaurant
import retrofit2.Response
import retrofit2.http.GET

interface BareatService {

    @GET("/api/restaurant")
    suspend fun getRestaurantList(): Response<List<Restaurant>>


}