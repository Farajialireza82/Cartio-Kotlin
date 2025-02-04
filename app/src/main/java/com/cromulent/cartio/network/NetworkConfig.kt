package com.cromulent.cartio.network

object NetworkConfig {
//    const val BASE_URL = "http://192.168.1.107:8080/api"
//    const val BASE_URL = "http://10.0.0.36:8080/api"
    const val BASE_URL = "http://10.0.2.2:8080/api"
    const val AUTH_ENDPOINT = "$BASE_URL/auth"
    const val SHOP_ITEMS_ENDPOINT = "$BASE_URL/shopItems"
    const val TIMEOUT = 6_000L
}