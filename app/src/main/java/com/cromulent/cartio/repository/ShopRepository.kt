package com.cromulent.cartio.repository

import com.cromulent.cartio.data.ShopItem

interface ShopRepository {
    suspend fun getAllShopItems(token: String?): Result<List<ShopItem>>

    suspend fun addShopItem(token: String?, shopItem: ShopItem): Result<Long>

    suspend fun editShopItem(token: String?, shopItem: ShopItem): Result<Unit>

    suspend fun deleteShopItem(token: String?, id: Long?): Result<Unit>

    suspend fun deleteShopItems(token: String?, ids: List<Long>?): Result<Unit>

    suspend fun markAsBought(token: String?, ids: List<Long>?): Result<Unit>
}