package com.cromulent.cartio.di

import com.cromulent.cartio.network.ShopItemApiService
import com.cromulent.cartio.repository.ShopRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single { provideRemoteRepository(get()) }
}

fun provideRemoteRepository(shopItemApiService: ShopItemApiService): ShopRepositoryImpl {
    return ShopRepositoryImpl(shopItemApiService)
}