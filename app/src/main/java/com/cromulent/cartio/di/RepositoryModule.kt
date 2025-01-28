package com.cromulent.cartio.di

import com.cromulent.cartio.network.AuthenticationApiService
import com.cromulent.cartio.network.ShopItemApiService
import com.cromulent.cartio.repository.ShopRepositoryImpl
import com.cromulent.cartio.repository.SignupRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single { provideSignUpRepository(get()) }
    single { provideRemoteRepository(get()) }
}

fun provideRemoteRepository(shopItemApiService: ShopItemApiService): ShopRepositoryImpl {
    return ShopRepositoryImpl(shopItemApiService)
}

fun provideSignUpRepository(authenticationApiService: AuthenticationApiService): SignupRepositoryImpl {
    return SignupRepositoryImpl(authenticationApiService)
}