package com.cromulent.cartio.network

import com.cromulent.cartio.network.dto.RegisterDTO
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType

class AuthenticationApiService(private val client: HttpClient) {

    companion object {
        private const val END_POINT = "http://10.0.0.36:8080/api/auth/register"
    }

    suspend fun createUser(data: RegisterDTO): HttpResponse {
        return client.post(urlString = END_POINT) {
            contentType(ContentType.Application.Json)
            setBody(data)
        }
    }
}