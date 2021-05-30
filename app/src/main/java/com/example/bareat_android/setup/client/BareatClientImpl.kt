package com.example.bareat_android.setup.client

import com.example.data.*
import com.example.data.torecieve.RegisterResponse
import com.example.data.tosend.RegisterBody
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


}