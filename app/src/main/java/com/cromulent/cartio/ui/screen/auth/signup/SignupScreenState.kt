package com.cromulent.cartio.ui.screen.auth.signup

data class SignupScreenState(
    val uiMode: SignupScreenUiMode = SignupScreenUiMode.NORMAL,
    val errorMessage: String? = null
)
enum class SignupScreenUiMode{
    SUCCESS, ERROR, NORMAL, LOADING
}