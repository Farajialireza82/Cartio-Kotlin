package com.cromulent.cartio.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cromulent.cartio.data.ShopItem
import com.cromulent.cartio.repository.ShopRepositoryImpl
import com.cromulent.cartio.state.ListPageState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ListPageViewModel(private val shopRepositoryImpl: ShopRepositoryImpl) : ViewModel() {
    private val _state: MutableStateFlow<ListPageState> = MutableStateFlow(ListPageState())
    val state: StateFlow<ListPageState> = _state

    fun loadItems() {
        viewModelScope.launch {
            shopRepositoryImpl.getAllShopItems().fold(
                onSuccess = { shopItems ->
                    _state.update {
                        it.copy(shopItems = shopItems.map { item ->
                            it.shopItems.find { existing -> existing.id == item.id } ?: item
                        })
                    }
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
                    val newShopItems = state.value.shopItems.map { item ->
                        if (item.id == shopItem.id) shopItem else item
                    }
                    _state.update { it.copy(shopItems = newShopItems) }
                }, onFailure = {
                    Log.e(TAG, "editShopItem: $it")
                    _state.update { it.copy(shopItems = _state.value.shopItems) }
                }
            )
        }
    }


    fun deleteShopItem(id: Long?) {
        viewModelScope.launch {
            // Keep a copy of the items before deletion for rollback
            val previousItems = state.value.shopItems

            // Optimistically update UI
            _state.update {
                it.copy(
                    shopItems = it.shopItems.filter { item -> item.id != id }
                )
            }

            shopRepositoryImpl.deleteShopItem(id).fold(
                onSuccess = {
                    // Success - UI is already updated
                },
                onFailure = {
                    Log.e(TAG, "deleteShopItem: $it")
                    // Rollback on failure
                    _state.update { it.copy(shopItems = previousItems) }
                }
            )
        }
    }
}

private const val TAG = "ListPageViewModel"