package com.cromulent.cartio.state

import com.cromulent.cartio.data.ShopItem

data class SignupScreenState(
    val uiMode: SignupScreenUiMode = SignupScreenUiMode.NORMAL
)
enum class SignupScreenUiMode{
    SUCCESS, ERROR, NORMAL
}