package com.cromulent.cartio.data.remote.api

import com.cromulent.cartio.data.model.ShopItem
import com.cromulent.cartio.data.remote.util.ApiResponse
import com.cromulent.cartio.network.NetworkConfig
import com.cromulent.cartio.data.remote.util.safeApiCall
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody

class ShopItemApiService(private val client: HttpClient) {
    suspend fun getAllShopItems(): ApiResponse<ArrayList<ShopItem>> = safeApiCall {
        client.get(NetworkConfig.SHOP_ITEMS_ENDPOINT).body()
    }

    suspend fun addShopItem(shopItem: ShopItem): ApiResponse<Long> = safeApiCall {
        val response = client.post(NetworkConfig.SHOP_ITEMS_ENDPOINT) {
            setBody(shopItem)
        }
        response.headers["Location"]?.split("/")?.last()?.toLong()
            ?: throw IllegalStateException("No ID returned from server")
    }

    suspend fun editShopItem(shopItem: ShopItem): ApiResponse<Unit> = safeApiCall {
        client.put(NetworkConfig.SHOP_ITEMS_ENDPOINT) {
            setBody(shopItem)
        }
    }

    suspend fun deleteShopItem(id: Long): ApiResponse<Unit> = safeApiCall {
        client.delete("${NetworkConfig.SHOP_ITEMS_ENDPOINT}/$id")
    }

    suspend fun deleteShopItems(ids: List<Long>): ApiResponse<Unit> = safeApiCall {
        client.post("${NetworkConfig.SHOP_ITEMS_ENDPOINT}/delete") {
            setBody(ids)
        }
    }

    suspend fun markAsBought(ids: List<Long>): ApiResponse<Unit> = safeApiCall {
        client.post("${NetworkConfig.SHOP_ITEMS_ENDPOINT}/markBought") {
            setBody(ids)
        }
    }
}