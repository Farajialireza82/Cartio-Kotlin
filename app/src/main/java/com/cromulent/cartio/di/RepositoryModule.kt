package com.cromulent.cartio.di

import com.cromulent.cartio.network.AuthenticationApiService
import com.cromulent.cartio.network.ShopItemApiService
import com.cromulent.cartio.repository.ShopRepositoryImpl
import com.cromulent.cartio.repository.AuthRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single { provideAuthRepository(get()) }
    single { provideRemoteRepository(get()) }
}

fun provideRemoteRepository(shopItemApiService: ShopItemApiService): ShopRepositoryImpl {
    return ShopRepositoryImpl(shopItemApiService)
}

fun provideAuthRepository(authenticationApiService: AuthenticationApiService): AuthRepositoryImpl {
    return AuthRepositoryImpl(authenticationApiService)
}