package com.cromulent.cartio.repository

import com.cromulent.cartio.data.ShopItem

interface ShopRepository {
    suspend fun getAllShopItems(): Result<List<ShopItem>>

    suspend fun addShopItem(shopItem: ShopItem): Result<Unit>

    suspend fun editShopItem(shopItem: ShopItem): Result<Unit>



}