package com.cromulent.cartio.state

import com.cromulent.cartio.data.ShopItem

data class LoginScreenState(
    val uiMode: LoginScreenUiMode = LoginScreenUiMode.NORMAL
)
enum class LoginScreenUiMode{
    SUCCESS, ERROR, NORMAL
}