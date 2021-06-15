package com.example.domain.usecase.profile

import com.example.data.DataBook
import com.example.data.Either
import com.example.data.Image
import com.example.domain.provider.ProfileProvider
import com.example.domain.provider.RestaurantProvider
import com.example.domain.usecase.base.BaseUseCaseWithParams

class GetBookListUseCase(
    private val profileProvider: ProfileProvider
) : BaseUseCaseWithParams<Either<String, List<DataBook>>, Int> {

    override suspend fun execute(params: Int): Either<String, List<DataBook>> = profileProvider.getBookList(params)

}