package com.cromulent.cartio.di

import android.app.Application
import org.koin.dsl.module

val appModule = module {
    single { get<Application>().applicationContext }
    includes(viewModelModule, networkModule, repositoryModule)
}