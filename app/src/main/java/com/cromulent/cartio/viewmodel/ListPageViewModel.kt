package com.cromulent.cartio.viewmodel

import android.app.Application
import android.content.Intent
import android.util.Log
import androidx.core.content.ContextCompat.startActivities
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cromulent.cartio.data.ShopItem
import com.cromulent.cartio.repository.ShopRepositoryImpl
import com.cromulent.cartio.state.ListPageState
import com.cromulent.cartio.state.ListPageUiMode
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
                        it.copy(shopItems = shopItems)
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

    fun toggleSelected(item: ShopItem) {

        val mSelectedItems = arrayListOf<ShopItem>()

        state.value.selectedItems.forEach {
            mSelectedItems.add(it)
        }

        mSelectedItems.apply {
            if (contains(item)) remove(item) else add(item)
        }

        _state.update {
            it.copy(
                selectedItems = mSelectedItems,
                uiMode = if (mSelectedItems.isEmpty()) ListPageUiMode.NORMAL else ListPageUiMode.SELECTING
            )
        }
    }

    fun clearItems() {
        _state.update {
            it.copy(
                uiMode = ListPageUiMode.NORMAL,
                selectedItems = arrayListOf()
            )
        }
    }

    fun deleteShopItems(deleteIds: List<Long>) {
        viewModelScope.launch {
            shopRepositoryImpl.deleteShopItems(deleteIds).fold(
                onSuccess = {
                    loadItems()
                },
                onFailure = {
                    Log.e(TAG, "deleteShopItems: $it")
                }
            )
        }

    }

    fun createShareText(): String {
        val toShareList = state.value.selectedItems.ifEmpty { state.value.shopItems }

        val toBuyItems = toShareList.filter { !it.isBought }
        val boughtItems = toShareList.filter { it.isBought }

        var shareText = "Hey. Please buy these items:\n" + toBuyItems.joinToString("\n") { item ->
            if (item.amount.isNullOrEmpty()) {
                "• ${item.name}"
            } else {
                "• ${item.name} (${item.amount})"
            }
        }
        if (boughtItems.isNotEmpty()) {
            shareText += "\n bought items:\n" + boughtItems.joinToString("\n") { item ->
                if (item.amount.isNullOrEmpty()) {
                    "• ${item.name}"
                } else {
                    "• ${item.name} (${item.amount})"
                }
            }
        }
        return shareText
    }

    fun markSelectedAsBought(boughtItems: List<ShopItem>) {
        val boughtIds = boughtItems.map { it.id!! }
        viewModelScope.launch {
            shopRepositoryImpl.markAsBought(boughtIds).fold(
                onSuccess = {
                    loadItems()
                }, onFailure = {
                    Log.e(TAG, "markSelectedAsBought: $it")
                }
            )
        }
    }
}

private const val TAG = "ListPageViewModel"