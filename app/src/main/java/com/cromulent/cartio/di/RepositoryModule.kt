package com.cromulent.cartio.di

import com.cromulent.cartio.data.remote.api.AuthenticationApiService
import com.cromulent.cartio.data.remote.api.ShopItemApiService
import com.cromulent.cartio.data.repository.auth.AuthRepository
import com.cromulent.cartio.data.repository.shop.ShopRepositoryImpl
import com.cromulent.cartio.data.repository.auth.AuthRepositoryImpl
import com.cromulent.cartio.data.repository.shop.ShopRepository
import com.cromulent.cartio.data.local.TokenRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { TokenRepository(get()) }
    single { provideAuthRepository(get(), get()) }
    single { provideShopRepository(get()) }
}

fun provideShopRepository(shopItemApiService: ShopItemApiService): ShopRepository =
    ShopRepositoryImpl(shopItemApiService)

fun provideAuthRepository(
    authenticationApiService: AuthenticationApiService,
    tokenRepository: TokenRepository
): AuthRepository = AuthRepositoryImpl(authenticationApiService, tokenRepository)