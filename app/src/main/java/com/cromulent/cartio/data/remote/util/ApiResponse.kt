package com.cromulent.cartio.data.remote.util

sealed class ApiResponse<out T> {
    data class Success<out T>(val data: T) : ApiResponse<T>()
    data class Error(val code: Int, val message: String) : ApiResponse<Nothing>()
    data class Exception(val e: Throwable) : ApiResponse<Nothing>()
}