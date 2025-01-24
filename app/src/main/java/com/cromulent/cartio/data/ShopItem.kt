package com.cromulent.cartio.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ShopItem(
    var id: Long?,
    var name: String,
    var amount: String?,
    @SerialName("is_bought")var isBought: Boolean
)
