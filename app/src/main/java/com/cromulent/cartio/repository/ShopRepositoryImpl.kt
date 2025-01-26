package com.cromulent.cartio.repository

import com.cromulent.cartio.data.ShopItem
import com.cromulent.cartio.network.ShopItemApiService
import io.ktor.client.call.body

class ShopRepositoryImpl(
    private val shopApi: ShopItemApiService
): ShopRepository {

    override suspend fun getAllShopItems(): Result<List<ShopItem>> {
        return runCatching {
            shopApi.getAllShopItems().body<List<ShopItem>>()
        }
    }

    override suspend fun addShopItem(shopItem: ShopItem): Result<Long> {
        return runCatching {
            val response = shopApi.addShopItem(shopItem)
            // Extract ID from Location header
            val locationHeader = response.headers["Location"]
            locationHeader?.let { location ->
                location.split("/").last().toLong()
            } ?: throw IllegalStateException("No ID returned from server")
        }
    }

    override suspend fun editShopItem(shopItem: ShopItem): Result<Unit> {
        return runCatching {
            shopApi.editShopItem(shopItem)
        }
    }

    override suspend fun deleteShopItem(id: Long?): Result<Unit> {
        return runCatching {
            shopApi.deleteShopItem(id)
        }
    }

    override suspend fun deleteShopItems(ids: List<Long>?): Result<Unit> {
        return runCatching {
            shopApi.deleteShopItems(ids)
        }
    }

}