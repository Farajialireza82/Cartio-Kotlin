package com.cromulent.cartio.di

import com.cromulent.cartio.network.AuthenticationApiService
import com.cromulent.cartio.network.ShopItemApiService
import com.cromulent.cartio.network.httpClientAndroid
import io.ktor.client.HttpClient
import org.koin.dsl.module

val networkModule = module {
    single { provideAuthenticationApiService(get()) }
    single { provideShopItemApiService(get()) }
    single { provideHttpClient() }
}

fun provideHttpClient(): HttpClient {
    return httpClientAndroid
}

fun provideShopItemApiService(httpClient: HttpClient): ShopItemApiService {
    return ShopItemApiService(httpClient)
}

fun provideAuthenticationApiService(httpClient: HttpClient): AuthenticationApiService {
    return AuthenticationApiService(httpClient)
}