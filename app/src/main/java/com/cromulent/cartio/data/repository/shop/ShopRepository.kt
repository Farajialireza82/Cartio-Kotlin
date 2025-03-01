package com.cromulent.cartio.data.repository.shop

import com.cromulent.cartio.data.model.ShopItem

interface ShopRepository {
    suspend fun getAllShopItems(): Result<List<ShopItem>>
    suspend fun addShopItem(shopItem: ShopItem): Result<Long>
    suspend fun editShopItem(shopItem: ShopItem): Result<Unit>
    suspend fun deleteShopItem(id: Long): Result<Unit>
    suspend fun deleteShopItems(ids: List<Long>): Result<Unit>
    suspend fun markAsBought(ids: List<Long>): Result<Unit>
}