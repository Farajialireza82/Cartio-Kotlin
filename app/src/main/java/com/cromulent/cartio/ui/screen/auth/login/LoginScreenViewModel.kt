package com.cromulent.cartio.ui.screen.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cromulent.cartio.data.remote.dto.LoginDTO
import com.cromulent.cartio.data.repository.auth.AuthRepositoryImpl
import com.cromulent.cartio.data.local.TokenRepository
import com.cromulent.cartio.data.repository.auth.AuthRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginScreenViewModel(
    private val authRepo: AuthRepository,
    private val tokenRepository: TokenRepository
) : ViewModel() {
    private val _state: MutableStateFlow<LoginScreenState> = MutableStateFlow(LoginScreenState())
    val state: StateFlow<LoginScreenState> = _state

    init {
        viewModelScope.launch {
            tokenRepository.getToken().collect { token ->

                if (!token.isNullOrEmpty()) {
                    _state.update {
                        it.copy(
                            uiMode = LoginScreenUiMode.SUCCESS
                        )
                    }
                }

            }
        }
    }

    fun userLogin(username: String, password: String) {
        _state.update {
            it.copy(
                uiMode = LoginScreenUiMode.LOADING
            )
        }
        viewModelScope.launch {
            delay(1000)
            authRepo.userLogin(LoginDTO(username, password)).fold(
                onSuccess = {
                    tokenRepository.saveToken(it.token)
                    _state.update {
                        it.copy(
                            uiMode = LoginScreenUiMode.SUCCESS
                        )
                    }
                },
                onFailure = { error ->
                    _state.update {
                        it.copy(
                            uiMode = LoginScreenUiMode.ERROR,
                            snackMessage = error.localizedMessage
                        )
                    }
                }
            )
        }
    }
}