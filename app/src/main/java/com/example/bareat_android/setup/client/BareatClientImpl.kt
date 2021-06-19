package com.example.bareat_android.setup.client

import com.example.data.*
import com.example.data.torecieve.*
import com.example.data.tosend.*
import com.example.domain.client.BareatClient
import com.example.domain.client.BareatService
import com.example.domain.client.MockClient
import com.example.domain.error.NetworkController

class BareatClientImpl(
private val bareatService: BareatService,
private val networkController: NetworkController,
private val mockClient: MockClient

) : BareatClient {
    override suspend fun doRegister(registerBody: RegisterBody): Either<String, RegisterResponse> {
        return try {
            val response = bareatService.doRegister(registerBody)
            networkController.checkResponse(response)
        } catch (e: Exception) {
            networkController.checkException(e)
        }
    }

    override suspend fun doLogin(loginBody: LoginBody): Either<String, LoginResponse> {
        return try {
            val response = bareatService.doLogin(loginBody)
            networkController.checkResponse(response)
        } catch (e: Exception) {
            networkController.checkException(e)
        }
    }

    override suspend fun getRestaurantList(isMock: Boolean): Either<String, List<Restaurant>> {
        return try {
            if (isMock) mockClient.getRestaurantList() else {
                val response = bareatService
                networkController.checkResponse(response.getRestaurantList())
            }
        } catch (e: java.lang.Exception) {
            networkController.checkException(e)
        }
    }

    override suspend fun getDishList(isMock: Boolean, id: Int): Either<String, List<Dish>> {
        return try {
            if (isMock) mockClient.getDishList(id) else {
                val response = bareatService
                networkController.checkResponse(response.getDishList(id))
            }
        } catch (e: java.lang.Exception) {
            networkController.checkException(e)
        }
    }

    override suspend fun getCommentList(isMock: Boolean, id: Int): Either<String, List<ReviewRestaurant>> {
        return try {
            if (isMock) mockClient.getCommentList(id) else {
                val response = bareatService
                networkController.checkResponse(response.getCommentList(id))
            }
        } catch (e: java.lang.Exception) {
            networkController.checkException(e)
        }
    }

    override suspend fun getImageList(id: Int): Either<String, List<Image>> {
        return try {
            val response = bareatService.getImageList(id)
            networkController.checkResponse(response)
        } catch (e: Exception) {
            networkController.checkException(e)
        }
    }

    override suspend fun getBookList(id: Int): Either<String, List<DataBook>> {
        return try {
            val response = bareatService.getBookList(id)
            networkController.checkResponse(response)
        } catch (e: Exception) {
            networkController.checkException(e)
        }
    }

    override suspend fun rateRestaurant(
        userId: Int,
        restaurantId: Int,
        rateRestaurantBody: RateRestaurantBody
    ): Either<String, RateRestaurantResponse> {
        return try {
            val response = bareatService.rateRestaurant(userId, restaurantId, rateRestaurantBody)
            networkController.checkResponse(response)
        } catch (e: Exception) {
            networkController.checkException(e)
        }
    }

    override suspend fun bookRestaurant(
        userId: Int,
        restaurantId: Int,
        bookBody: BookBody
    ): Either<String, BookResponse> {
        return try {
            val response = bareatService.bookRestaurant(userId, restaurantId, bookBody)
            networkController.checkResponse(response)
        } catch (e: Exception) {
            networkController.checkException(e)
        }
    }

    override suspend fun rateDish(
        userId: Int,
        dishId: Int,
        rateDishBody: RateDishBody
    ): Either<String, RateDishResponse> {
        return try {
            val response = bareatService.rateDish(userId, dishId, rateDishBody)
            networkController.checkResponse(response)
        } catch (e: Exception) {
            networkController.checkException(e)
        }
    }

    override suspend fun getCommentListDish(dishId: Int): Either<String, List<ReviewDish>> {
        return try {
            val response = bareatService.getCommentListDish(dishId)
            networkController.checkResponse(response)
        } catch (e: Exception) {
            networkController.checkException(e)
        }
    }

    override suspend fun deleteBook(userId: Int, bookId: Int): Either<String, Any> {
        return try {
            val response = bareatService.deleteBook(userId, bookId)
            networkController.checkResponse(response)
        } catch (e: Exception) {
            networkController.checkException(e)
        }
    }

    override suspend fun getUserProductReviews(userId: Int): Either<String, List<ReviewDish>> {
        return try {
            val response = bareatService.getUserProductReviews(userId)
            networkController.checkResponse(response)
        } catch (e: Exception) {
            networkController.checkException(e)
        }
    }

    override suspend fun getUserRestaurantReviews(userId: Int): Either<String, List<ReviewRestaurant>> {
        return try {
            val response = bareatService.getUserRestaurantReviews(userId)
            networkController.checkResponse(response)
        } catch (e: Exception) {
            networkController.checkException(e)
        }
    }

    override suspend fun editProductReview(
        reviewId: Int,
        rateDishBody: RateDishBody
    ): Either<String, Any> {
        return try {
            val response = bareatService.editProductReview(reviewId, rateDishBody)
            networkController.checkResponse(response)
        } catch (e: Exception) {
            networkController.checkException(e)
        }
    }

    override suspend fun editRestaurantReview(
        reviewId: Int,
        rateRestaurantBody: RateRestaurantBody
    ): Either<String, Any> {
        return try {
            val response = bareatService.editRestaurantReview(reviewId, rateRestaurantBody)
            networkController.checkResponse(response)
        } catch (e: Exception) {
            networkController.checkException(e)
        }
    }

    override suspend fun deleteProductReview(bookId: Int): Either<String, Any> {
        return try {
            val response = bareatService.deleteProductReview(bookId)
            networkController.checkResponse(response)
        } catch (e: Exception) {
            networkController.checkException(e)
        }
    }

    override suspend fun deleteRestaurantReview(bookId: Int): Either<String, Any> {
        return try {
            val response = bareatService.deleteRestaurantReview(bookId)
            networkController.checkResponse(response)
        } catch (e: Exception) {
            networkController.checkException(e)
        }
    }


}