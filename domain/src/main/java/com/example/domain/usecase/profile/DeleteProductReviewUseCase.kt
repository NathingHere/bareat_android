package com.example.domain.usecase.profile

import com.example.data.Either
import com.example.domain.provider.ProfileProvider
import com.example.domain.usecase.base.BaseUseCaseWithParams

class DeleteProductReviewUseCase(
    private val profileProvider: ProfileProvider
) : BaseUseCaseWithParams<Either<String, Any>, Int> {
    override suspend fun execute(params: Int): Either<String, Any> = profileProvider.deleteProductReview(params)
}