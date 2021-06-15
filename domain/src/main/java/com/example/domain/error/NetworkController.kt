package com.example.domain.error

import com.example.data.*
import retrofit2.Response
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class NetworkController {

    fun checkException(e: Exception): Either<String, Nothing> {
        return when (e) {
            is UnknownHostException -> bareatError(MessageError.networkDown)
            is SocketTimeoutException -> bareatError(MessageError.timeOut)
            is NullPointerException -> bareatError(MessageError.notFound)
            else -> bareatError(MessageError.errorGeneral)
        }
    }

    fun <T : Any> checkResponse(response: Response<T>): Either<String, T> {

        return when (response.code()) {

            in 200..299 -> if (response.body() != null) {
                value(response.body() as T)
            } else bareatError(response.errorBody().toString())

            else -> bareatError(checkError(response.code()))
        }
    }

    private fun checkError(errorCode: Int): String {
        return when (errorCode) {
            303 -> MessageError.wrongLogin
            401 -> MessageError.unauthorized
            403 -> MessageError.forbidden
            404 -> MessageError.notFound
            405 -> MessageError.methodNotAllowed
            422 -> MessageError.emailRegistered
            in 500..503 -> MessageError.networkDown
            800 -> MessageError.updateApp
            else -> MessageError.errorGeneral
        }
    }
}