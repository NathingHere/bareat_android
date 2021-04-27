package com.example.domain.client

import com.example.data.Either
import com.example.data.Restaurant

interface MockClient {

    suspend fun getRestaurantRatedList(): Either<String, List<Restaurant>>

}