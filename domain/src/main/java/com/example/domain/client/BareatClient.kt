package com.example.domain.client

import com.example.data.*
import com.example.data.torecieve.*
import com.example.data.tosend.*

interface BareatClient {

    suspend fun doRegister(registerBody: RegisterBody): Either<String, RegisterResponse>

    suspend fun doLogin(loginBody: LoginBody): Either<String, LoginResponse>

    suspend fun getRestaurantList(isMock: Boolean = false): Either<String, List<Restaurant>>

    suspend fun getDishList(isMock: Boolean = false, id: Int): Either<String, List<Dish>>

    suspend fun getCommentList(isMock: Boolean = false, id: Int): Either<String, List<ReviewRestaurant>>

    suspend fun getImageList(id: Int): Either<String, List<Image>>

    suspend fun getBookList(id: Int): Either<String, List<DataBook>>

    suspend fun rateRestaurant(userId: Int, restaurantId: Int, rateRestaurantBody: RateRestaurantBody): Either<String, RateRestaurantResponse>

    suspend fun bookRestaurant(userId: Int, restaurantId: Int, bookBody: BookBody): Either<String, BookResponse>

    suspend fun rateDish(userId: Int, dishId: Int, rateDishBody: RateDishBody): Either<String, RateDishResponse>

    suspend fun getCommentListDish(dishId: Int): Either<String, List<ReviewDish>>

    suspend fun deleteBook(userId: Int, bookId: Int): Either<String, Any>

    suspend fun getUserProductReviews(userId: Int): Either<String, List<ReviewDish>>

    suspend fun getUserRestaurantReviews(userId: Int): Either<String, List<ReviewRestaurant>>

    suspend fun editProductReview(reviewId: Int, rateDishBody: RateDishBody): Either<String, Any>

    suspend fun editRestaurantReview(reviewId: Int, rateRestaurantBody: RateRestaurantBody): Either<String, Any>

    suspend fun deleteProductReview(bookId: Int): Either<String, Any>

    suspend fun deleteRestaurantReview(bookId: Int): Either<String, Any>
}