package com.cromulent.cartio.utils

fun getItemsText(count: Int): String {
    return if (count == 1) "$count item"
    else "$count items"
}