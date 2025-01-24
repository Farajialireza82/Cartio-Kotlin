package com.cromulent.cartio.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cromulent.cartio.data.ShopItem
import com.cromulent.cartio.repository.ShopRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ListPageViewModel(private val shopRepositoryImpl: ShopRepositoryImpl) : ViewModel() {
    private val _items: MutableStateFlow<List<ShopItem>> = MutableStateFlow(emptyList())
    val items: StateFlow<List<ShopItem>> = _items

    fun loadItems() {
        viewModelScope.launch {
            shopRepositoryImpl.getAllShopItems().fold(
                onSuccess = { shopItems ->
                    _items.update { shopItems }
                }, onFailure = {
                    Log.e(TAG, "loadItems: $it ")
                }
            )
        }
    }

    fun addItem(name: String, quantity: String) {
        viewModelScope.launch {
            shopRepositoryImpl.addShopItem(ShopItem(null, name, quantity, false))
                .fold(
                    onSuccess = {
                        loadItems()
                    }, onFailure = {
                        Log.e(TAG, "addItem: $it")
                    }
                )
        }
    }

    fun editShopItem(shopItem: ShopItem) {
        viewModelScope.launch {
            shopRepositoryImpl.editShopItem(shopItem).fold(
                onSuccess = {

                }, onFailure = {
                    Log.e(TAG, "editShopItem: $it")
                }
            )
        }
    }
}

private const val TAG = "ListPageViewModel"