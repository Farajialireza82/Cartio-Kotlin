package com.cromulent.cartio.state

import com.cromulent.cartio.data.ShopItem

data class ListPageState(
    val shopItems: List<ShopItem> = arrayListOf()
)