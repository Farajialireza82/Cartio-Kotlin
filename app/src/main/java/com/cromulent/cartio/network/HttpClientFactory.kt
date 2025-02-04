package com.cromulent.cartio.network

import android.util.Log
import com.cromulent.cartio.network.interceptor.AuthInterceptor
import com.cromulent.cartio.network.plugin.createAuthPlugin
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class HttpClientFactory(private val authInterceptor: AuthInterceptor) {
    fun create(): HttpClient = HttpClient(Android) {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                useAlternativeNames = true
                ignoreUnknownKeys = true
                encodeDefaults = false
            })
        }

        install(HttpTimeout) {
            requestTimeoutMillis = NetworkConfig.TIMEOUT
            connectTimeoutMillis = NetworkConfig.TIMEOUT
            socketTimeoutMillis = NetworkConfig.TIMEOUT
        }

        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    Log.v("Logger Ktor =>", message)
                }
            }
            level = LogLevel.BODY
        }

        install(DefaultRequest) {
            contentType(ContentType.Application.Json)
        }

        // Custom plugin for auth
        install(createAuthPlugin(authInterceptor))
    }
}
