package com.cromulent.cartio.network.plugin

import com.cromulent.cartio.network.interceptor.AuthInterceptor
import io.ktor.client.plugins.api.createClientPlugin

fun createAuthPlugin(authInterceptor: AuthInterceptor) = createClientPlugin("AuthPlugin") {
    onRequest { request, _ ->
        authInterceptor.intercept(request)
    }
}