package com.cromulent.cartio.state

import com.cromulent.cartio.data.ShopItem

data class ListPageState(
    val shopItems: List<ShopItem> = arrayListOf(),
    val selectedItems: ArrayList<ShopItem> = arrayListOf(),
    val uiMode: ListPageUiMode = ListPageUiMode.NORMAL
)
enum class ListPageUiMode{
    LOADING, SELECTING, NORMAL, LOGOUT
}