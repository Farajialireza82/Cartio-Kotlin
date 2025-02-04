package com.cromulent.cartio.ui.screen.shop.list

import com.cromulent.cartio.data.model.ShopItem

data class ListPageState(
    val shopItems: List<ShopItem> = arrayListOf(),
    val selectedItems: ArrayList<ShopItem> = arrayListOf(),
    val uiMode: ListPageUiMode = ListPageUiMode.NORMAL,
    val snackMessage: String? = null
)
enum class ListPageUiMode{
    LOADING, SELECTING, NORMAL, LOGOUT, ERROR
}