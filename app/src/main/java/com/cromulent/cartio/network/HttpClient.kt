package com.cromulent.cartio.network

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.observer.ResponseObserver
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

private const val NETWORK_TIME_OUT = 6_000L

val httpClientAndroid = HttpClient(Android){
    install(ContentNegotiation){
        json(
            Json {
                prettyPrint = true
                isLenient = true
                useAlternativeNames = true
                ignoreUnknownKeys = true
                encodeDefaults = false
            }
        )
    }

    install(HttpTimeout){
        requestTimeoutMillis = NETWORK_TIME_OUT
        connectTimeoutMillis = NETWORK_TIME_OUT
        socketTimeoutMillis = NETWORK_TIME_OUT
    }

    install(Logging){
        logger = object : Logger {
            override fun log(message: String) {
                Log.v("Logger Ktor =>", message)
            }
        }
        level = LogLevel.ALL
    }

    install(ResponseObserver){
        onResponse {
            Log.d("HTTP status:", "${it.status.value}")
        }
    }

    install(DefaultRequest){
        header(HttpHeaders.ContentType, ContentType.Application.Any)
//        header(HttpHeaders.Authorization, "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbGkxIiwiaWF0IjoxNzM4MDYzNzcyLCJleHAiOjE3MzgwNjUyMTJ9.DEDZUOjDCjVhmuFxrKmZyPFpm7kvBhkEd-1n3JXf7Wc")
    }
}