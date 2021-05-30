package com.example.domain.client

import com.example.data.Dish
import com.example.data.Image
import com.example.data.Restaurant
import com.example.data.ReviewRestaurant
import com.example.data.torecieve.RegisterResponse
import com.example.data.tosend.LoginBody
import com.example.data.tosend.RegisterBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface BareatService {

    //ONBOARDING

    //@POST("api/login")
    //suspend fun doLogin(@Body loginBody: LoginBody): Response<LoginResponse>

    @POST("api/usuarios")
    suspend fun doRegister(@Body registerBody: RegisterBody): Response<RegisterResponse>

    //MAIN

    @GET("api/establecimientos")
    suspend fun getRestaurantList(): Response<List<Restaurant>>

    @GET("api/establecimiento/{id}/productos")
    suspend fun getDishList(@Path("id") id: Int): Response<List<Dish>>

    @GET("api/establecimiento/{id}/puntuaciones")
    suspend fun getCommentList(@Path("id") id: Int): Response<List<ReviewRestaurant>>

    @GET("api/establecimiento/{id}/imagenes")
    suspend fun getImageList(@Path("id") id: Int): Response<List<Image>>

}