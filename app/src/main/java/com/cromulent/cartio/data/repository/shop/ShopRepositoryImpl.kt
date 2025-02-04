package com.cromulent.cartio.data.repository.shop

import com.cromulent.cartio.data.model.ShopItem
import com.cromulent.cartio.data.remote.util.ApiResponse
import com.cromulent.cartio.data.remote.api.ShopItemApiService

class ShopRepositoryImpl(private val shopApi: ShopItemApiService) : ShopRepository {
    override suspend fun getAllShopItems(): Result<ArrayList<ShopItem>> =
        shopApi.getAllShopItems().toResult()

    override suspend fun addShopItem(shopItem: ShopItem): Result<Long> =
        shopApi.addShopItem(shopItem).toResult()

    override suspend fun editShopItem(shopItem: ShopItem): Result<Unit> =
        shopApi.editShopItem(shopItem).toResult()

    override suspend fun deleteShopItem(id: Long): Result<Unit> =
        shopApi.deleteShopItem(id).toResult()

    override suspend fun deleteShopItems(ids: List<Long>): Result<Unit> =
        shopApi.deleteShopItems(ids).toResult()

    override suspend fun markAsBought(ids: List<Long>): Result<Unit> =
        shopApi.markAsBought(ids).toResult()

    private fun <T> ApiResponse<T>.toResult(): Result<T> = when (this) {
        is ApiResponse.Success -> Result.success(data)
        is ApiResponse.Error -> Result.failure(Exception(message))
        is ApiResponse.Exception -> Result.failure(e)
    }
}