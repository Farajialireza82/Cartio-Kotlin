package com.cromulent.cartio.network

import com.cromulent.cartio.data.ShopItem
import io.ktor.client.HttpClient
import io.ktor.client.request.bearerAuth
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
        private const val END_POINT = "http://10.0.0.36:8080/shopItems"
    }

    suspend fun getAllShopItems(token: String?): HttpResponse{
        return client.get(END_POINT){
            bearerAuth(token!!)
        }
    }

    suspend fun addShopItem(token: String?, shopItem: ShopItem): HttpResponse {
        return client.post(urlString = END_POINT) {
            bearerAuth(token!!)
            contentType(ContentType.Application.Json)
            setBody(shopItem)
        }
    }

    suspend fun editShopItem(token: String?, shopItem: ShopItem) {
        client.put(urlString = END_POINT) {
            bearerAuth(token!!)
            contentType(ContentType.Application.Json)
            setBody(shopItem)
        }
    }

    suspend fun deleteShopItem(token: String?, id: Long?) {
        client.delete(urlString = END_POINT + "/$id"){
            bearerAuth(token!!)
        }
    }

    suspend fun deleteShopItems(token: String?, ids: List<Long>?) {
        client.post(urlString = END_POINT + "/delete") {
            bearerAuth(token!!)
            contentType(ContentType.Application.Json)
            setBody(ids)
        }
    }

    suspend fun markAsBought(token: String?, ids: List<Long>?){
        client.post(urlString = END_POINT + "/markBought") {
            contentType(ContentType.Application.Json)
            bearerAuth(token!!)
            setBody(ids)
        }
    }
}