package com.example.domain.client

import com.example.data.*
import com.example.data.torecieve.*
import com.example.data.tosend.*
import retrofit2.Response
import retrofit2.http.*

interface BareatService {

    //ONBOARDING

    @POST("api/login")
    suspend fun doLogin(@Body loginBody: LoginBody): Response<LoginResponse>

    @POST("api/usuarios")
    suspend fun doRegister(@Body registerBody: RegisterBody): Response<RegisterResponse>

    //MAIN

    @GET("api/establecimientos")
    suspend fun getRestaurantList(): Response<List<Restaurant>>

    @GET("api/establecimiento/{id}/productos")
    suspend fun getDishList(@Path("id") id: Int): Response<List<Dish>>

    @GET("api/establecimiento/{id}/puntuaciones")
    suspend fun getCommentList(@Path("id") id: Int): Response<List<ReviewRestaurant>>

    @GET("api/producto/{id}/puntuaciones")
    suspend fun getCommentListDish(@Path("id") id: Int): Response<List<ReviewDish>>

    @GET("api/establecimiento/{id}/imagenes")
    suspend fun getImageList(@Path("id") id: Int): Response<List<Image>>

    @GET("api/usuario/{id}/reservas")
    suspend fun getBookList(@Path("id") id: Int): Response<List<DataBook>>

    @POST("api/usuario/{userId}/establecimiento/{restaurantId}/puntuacion")
    suspend fun rateRestaurant(@Path("userId") userId: Int, @Path("restaurantId") restaurantId: Int, @Body rateRestaurantBody: RateRestaurantBody): Response<RateRestaurantResponse>

    @POST("api/usuario/{userId}/establecimiento/{restaurantId}/reserva")
    suspend fun bookRestaurant(@Path("userId") userId: Int, @Path("restaurantId") restaurantId: Int, @Body bookBody: BookBody): Response<BookResponse>

    @POST("api/usuario/{userId}/producto/{dishId}/puntuacion")
    suspend fun rateDish(@Path("userId") userId: Int, @Path("dishId") dishId: Int, @Body rateDishBody: RateDishBody): Response<RateDishResponse>

    @DELETE("api/usuario/{userId}/reservas/{bookId}")
    suspend fun deleteBook(@Path("userId") userId: Int, @Path("bookId") bookId: Int): Response<Any>

}