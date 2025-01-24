package com.cromulent.cartio.di

import com.cromulent.cartio.network.ShopItemApiService
import com.cromulent.cartio.network.httpClientAndroid
import io.ktor.client.HttpClient
import org.koin.dsl.module

val networkModule = module {
    single { provideShopItemApiService(get()) }
    single { provideHttpClient() }
}

fun provideHttpClient(): HttpClient {
    return httpClientAndroid
}

fun provideShopItemApiService(httpClient: HttpClient): ShopItemApiService {
    return ShopItemApiService(httpClient)
}