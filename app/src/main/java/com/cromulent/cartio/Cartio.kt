package com.cromulent.cartio

import android.app.Application
import com.cromulent.cartio.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class Cartio: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@Cartio)
            modules(appModule)
        }


    }
}