package com.cromulent.cartio.ui.screen.auth.login

data class LoginScreenState(
    val uiMode: LoginScreenUiMode = LoginScreenUiMode.NORMAL,
    val snackMessage: String? = null
)
enum class LoginScreenUiMode{
    SUCCESS, ERROR, NORMAL, LOADING
}