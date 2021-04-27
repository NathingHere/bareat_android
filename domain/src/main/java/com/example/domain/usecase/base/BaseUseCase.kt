package com.example.domain.usecase.base

interface BaseUseCase<T> {

    suspend fun execute(): T
}