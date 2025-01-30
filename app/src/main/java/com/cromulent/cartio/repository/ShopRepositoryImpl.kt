package com.cromulent.cartio.repository

import com.cromulent.cartio.data.ShopItem
import com.cromulent.cartio.network.ShopItemApiService
import io.ktor.client.call.body

class ShopRepositoryImpl(
    private val shopApi: ShopItemApiService
): ShopRepository {

    override suspend fun getAllShopItems(token: String?): Result<List<ShopItem>> {
        return runCatching {
            shopApi.getAllShopItems(token).body<List<ShopItem>>()
        }
    }

    override suspend fun addShopItem(token: String?, shopItem: ShopItem): Result<Long> {
        return runCatching {
            val response = shopApi.addShopItem(token, shopItem)
            // Extract ID from Location header
            val locationHeader = response.headers["Location"]
            locationHeader?.let { location ->
                location.split("/").last().toLong()
            } ?: throw IllegalStateException("No ID returned from server")
        }
    }

    override suspend fun editShopItem(token: String?, shopItem: ShopItem): Result<Unit> {
        return runCatching {
            shopApi.editShopItem(token, shopItem)
        }
    }

    override suspend fun deleteShopItem(token: String?, id: Long?): Result<Unit> {
        return runCatching {
            shopApi.deleteShopItem(token, id)
        }
    }

    override suspend fun deleteShopItems(token: String?, ids: List<Long>?): Result<Unit> {
        return runCatching {
            shopApi.deleteShopItems(token, ids)
        }
    }

    override suspend fun markAsBought(token: String?, ids: List<Long>?): Result<Unit> {
        return runCatching {
            shopApi.markAsBought(token, ids)
        }
    }

}