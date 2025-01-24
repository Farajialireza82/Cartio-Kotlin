package com.cromulent.cartio.di

import org.koin.dsl.module

val appModule = module {
    includes(viewModelModule, networkModule, repositoryModule)
}