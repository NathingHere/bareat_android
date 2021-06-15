package com.example.domain.repository

import com.example.data.DataBook
import com.example.data.Either
import com.example.domain.client.BareatClient

class ProfileRepository(
    private val bareatClient: BareatClient
) {

    suspend fun getBookList(id: Int): Either<String, List<DataBook>> = bareatClient.getBookList(id)

}