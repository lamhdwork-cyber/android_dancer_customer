package com.kantek.dancer.booking.app

import com.google.gson.Gson
import com.kantek.dancer.booking.data.helper.network.model.ApiError
import com.kantek.dancer.booking.data.helper.network.model.ApiValidError

class InternalServerException : RuntimeException("Error internal server")
class ExpiredTokenException : RuntimeException("Unauthorized !")
class ServerResponseNullException : RuntimeException("Server response no Content")
class ParameterInvalidException(message: String) : RuntimeException(message)

open class ApiRequestException(rawMessage: String?) : RuntimeException() {
    private val mMessage = rawMessage
        ?.let {
            try {
                Gson().fromJson(it, ApiError::class.java)
            } catch (e: Throwable) {
                ApiError(0, rawMessage, null, mutableListOf())
            }
        }
        ?.takeIf { it.body.isNotEmpty() }?.body ?: "Unknown"

    override val message: String
        get() = mMessage
}

open class JobChangeAssignException(rawMessage: String?) : RuntimeException() {
    private val mMessage = rawMessage
        ?.let {
            try {
                Gson().fromJson(it, ApiValidError::class.java)
            } catch (e: Throwable) {
                ApiValidError(rawMessage)
            }
        }
        ?.takeIf { it.body.isNotEmpty() }?.body ?: "Unknown"

    override val message: String
        get() = mMessage
}
