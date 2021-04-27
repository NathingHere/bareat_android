package com.example.bareat_android.setup.client

import com.example.bareat_android.BuildConfig
import com.example.data.Either
import com.example.data.Restaurant
import com.example.domain.client.BareatClient
import com.example.domain.client.BareatService
import com.example.domain.client.MockClient
import com.example.domain.error.NetworkController

class BareatClientImpl(
private val bareatService: BareatService,
private val networkController: NetworkController,
private val mockClient: MockClient

) : BareatClient {

    override suspend fun getRestaurantRatedList(isMock: Boolean): Either<String, List<Restaurant>> {
        return try {
            if (isMock) mockClient.getRestaurantRatedList() else {
                val response = bareatService
                networkController.checkResponse(response.getRestaurantList())
            }
        } catch (e: java.lang.Exception) {
            networkController.checkException(e)
        }
    }


}