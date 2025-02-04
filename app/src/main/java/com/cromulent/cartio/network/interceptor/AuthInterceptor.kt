package com.cromulent.cartio.network.interceptor

import com.cromulent.cartio.data.local.TokenRepository
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.headers
import io.ktor.http.HttpHeaders
import kotlinx.coroutines.flow.firstOrNull

class AuthInterceptor(private val tokenRepository: TokenRepository) {
    suspend fun intercept(request: HttpRequestBuilder) {
        tokenRepository.getToken().firstOrNull()?.let { token ->
            if (token.isNotEmpty()) {
                request.headers {
                    append(HttpHeaders.Authorization, "Bearer $token")
                }
            }
        }
    }
}