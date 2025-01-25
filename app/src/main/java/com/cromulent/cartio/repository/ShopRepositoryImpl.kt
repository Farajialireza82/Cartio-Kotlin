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

    override suspend fun addShopItem(shopItem: ShopItem): Result<Unit> {
        return runCatching {
            shopApi.addShopItem(shopItem)
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

}