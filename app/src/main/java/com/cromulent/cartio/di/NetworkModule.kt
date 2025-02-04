package com.cromulent.cartio.di

import com.cromulent.cartio.network.interceptor.AuthInterceptor
import com.cromulent.cartio.data.remote.api.AuthenticationApiService
import com.cromulent.cartio.network.HttpClientFactory
import com.cromulent.cartio.data.remote.api.ShopItemApiService
import io.ktor.client.HttpClient
import org.koin.dsl.module

val networkModule = module {
    single { AuthInterceptor(get()) }
    single { HttpClientFactory(get()).create() }
    single { provideAuthenticationApiService(get()) }
    single { provideShopItemApiService(get()) }
}

fun provideShopItemApiService(httpClient: HttpClient): ShopItemApiService {
    return ShopItemApiService(httpClient)
}

fun provideAuthenticationApiService(httpClient: HttpClient): AuthenticationApiService {
    return AuthenticationApiService(httpClient)
}