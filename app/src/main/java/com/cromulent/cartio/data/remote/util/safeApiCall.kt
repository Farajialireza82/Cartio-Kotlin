package com.cromulent.cartio.data.remote.util

import io.ktor.client.plugins.ClientRequestException

suspend fun <T> safeApiCall(apiCall: suspend () -> T): ApiResponse<T> = try {
    ApiResponse.Success(apiCall())
} catch (e: ClientRequestException) {
    ApiResponse.Error(e.response.status.value, e.message)
} catch (e: Exception) {
    ApiResponse.Exception(e)
}