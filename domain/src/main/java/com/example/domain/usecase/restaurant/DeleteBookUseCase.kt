package com.example.domain.usecase.restaurant

import com.example.data.Either
import com.example.data.torecieve.BookResponse
import com.example.data.tosend.BookBody
import com.example.domain.provider.RestaurantProvider
import com.example.domain.usecase.base.BaseUseCaseWithParams

class DeleteBookUseCase(
    private val restaurantProvider: RestaurantProvider
) : BaseUseCaseWithParams<Either<String, Any>, ParamsDeleteBook> {
    override suspend fun execute(params: ParamsDeleteBook): Either<String, Any> = restaurantProvider.deleteBook(params.userId, params.bookId)
}

data class ParamsDeleteBook (
    var userId: Int,
    var bookId: Int
)