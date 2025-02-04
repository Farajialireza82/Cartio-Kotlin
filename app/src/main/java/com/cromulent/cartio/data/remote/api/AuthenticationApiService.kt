package com.cromulent.cartio.data.remote.api

import com.cromulent.cartio.data.remote.dto.LoginDTO
import com.cromulent.cartio.data.remote.dto.RegisterDTO
import com.cromulent.cartio.data.remote.dto.TokenDTO
import com.cromulent.cartio.data.remote.util.ApiResponse
import com.cromulent.cartio.network.NetworkConfig
import com.cromulent.cartio.data.remote.util.safeApiCall
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody

class AuthenticationApiService(private val client: HttpClient) {
    suspend fun createUser(data: RegisterDTO): ApiResponse<TokenDTO> = safeApiCall {
        client.post("${NetworkConfig.AUTH_ENDPOINT}/register") {
            setBody(data)
        }.body()
    }

    suspend fun userLogin(data: LoginDTO): ApiResponse<TokenDTO> = safeApiCall {
        client.post("${NetworkConfig.AUTH_ENDPOINT}/authenticate") {
            setBody(data)
        }.body()
    }
}