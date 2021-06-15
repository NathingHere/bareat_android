package com.example.domain.provider

import com.example.data.DataBook
import com.example.data.Either
import com.example.domain.repository.ProfileRepository

class ProfileProvider(
    private val profileRepository: ProfileRepository
) {

    suspend fun getBookList(id: Int): Either<String, List<DataBook>> = profileRepository.getBookList(id)

}