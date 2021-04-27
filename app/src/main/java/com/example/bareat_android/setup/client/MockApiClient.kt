package com.example.bareat_android.setup.client

import com.example.data.Either
import com.example.data.Restaurant
import com.example.data.value
import com.example.domain.client.MockClient

class MockApiClient : MockClient {

    override suspend fun getRestaurantRatedList(): Either<String, List<Restaurant>> = value(mockRestaurantList)

    private val mockRestaurantList = listOf(
        Restaurant("qweerwwedf dg fddfg dtgerd dfg", "qweqweqw", "https://media-cdn.tripadvisor.com/media/photo-s/1a/b8/46/6d/london-stock.jpg", "qweqqw qweqwe", 2.323, 2.23, "restaurante", 120, 4.15F, true),
        Restaurant("qweerwwedf", "qweqweqw", "https://media-cdn.tripadvisor.com/media/photo-s/1a/b8/46/6d/london-stock.jpg", "qweqqw qweqwe", 2.323, 2.23, "restaurante", 120, 4.76F, false),
        Restaurant("qweerwwedf", "qweqweqw", "https://media-cdn.tripadvisor.com/media/photo-s/1a/b8/46/6d/london-stock.jpg", "qweqqw qweqwe", 2.323, 2.23, "restaurante", 120, 2.34F, true),
        Restaurant("qweerwwedf", "qweqweqw", "https://media-cdn.tripadvisor.com/media/photo-s/1a/b8/46/6d/london-stock.jpg", "qweqqw qweqwe", 2.323, 2.23, "restaurante", 120, 2.34F, true),
        Restaurant("qweerwwedf", "qweqweqw", "https://media-cdn.tripadvisor.com/media/photo-s/1a/b8/46/6d/london-stock.jpg", "qweqqw qweqwe", 2.323, 2.23, "restaurante", 120, 2.34F, true),
        Restaurant("qweerwwedf", "qweqweqw", "https://media-cdn.tripadvisor.com/media/photo-s/1a/b8/46/6d/london-stock.jpg", "qweqqw qweqwe", 2.323, 2.23, "restaurante", 120, 2.34F, true),
        Restaurant("qweerwwedf", "qweqweqw", "https://media-cdn.tripadvisor.com/media/photo-s/1a/b8/46/6d/london-stock.jpg", "qweqqw qweqwe", 2.323, 2.23, "restaurante", 120, 2.34F, true),
        Restaurant("qweerwwedf", "qweqweqw", "https://media-cdn.tripadvisor.com/media/photo-s/1a/b8/46/6d/london-stock.jpg", "qweqqw qweqwe", 2.323, 2.23, "restaurante", 120, 2.34F, true),
        Restaurant("qweerwwedf", "qweqweqw", "https://media-cdn.tripadvisor.com/media/photo-s/1a/b8/46/6d/london-stock.jpg", "qweqqw qweqwe", 2.323, 2.23, "restaurante", 120, 2.34F, true)
        )

}