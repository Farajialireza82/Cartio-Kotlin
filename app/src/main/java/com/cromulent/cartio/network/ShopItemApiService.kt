package com.cromulent.cartio.network

import com.cromulent.cartio.data.ShopItem
import io.ktor.client.HttpClient
import io.ktor.client.request.HttpRequest
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.invoke
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType

class ShopItemApiService(private val client: HttpClient) {

    companion object {
        private const val END_POINT = "http://10.0.2.2:8080/"
    }

    suspend fun getAllShopItems() = client.get(END_POINT + "shopItems")

    suspend fun addShopItem(shopItem: ShopItem) {
        client.post(urlString = END_POINT + "shopItems") {
            contentType(ContentType.Application.Json)
            setBody(shopItem)
        }
    }

    suspend fun editShopItem(shopItem: ShopItem) {
        client.put(urlString = END_POINT + "shopItems") {
            contentType(ContentType.Application.Json)
            setBody(shopItem)
        }
    }

    suspend fun deleteShopItem(id: Long?) {
        client.delete(urlString = END_POINT + "shopItems/$id")
    }
}