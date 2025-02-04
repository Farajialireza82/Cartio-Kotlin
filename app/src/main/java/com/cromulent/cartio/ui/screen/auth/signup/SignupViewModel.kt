package com.cromulent.cartio.ui.screen.auth.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cromulent.cartio.data.remote.dto.RegisterDTO
import com.cromulent.cartio.data.repository.auth.AuthRepositoryImpl
import com.cromulent.cartio.ui.screen.auth.login.LoginScreenState
import com.cromulent.cartio.ui.screen.auth.login.LoginScreenUiMode
import com.cromulent.cartio.data.local.TokenRepository
import com.cromulent.cartio.data.repository.auth.AuthRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignupViewModel(
    private val authRepo: AuthRepository,
    private val tokenRepository: TokenRepository
) : ViewModel() {
    private val _state: MutableStateFlow<SignupScreenState> = MutableStateFlow(SignupScreenState())
    val state: StateFlow<SignupScreenState> = _state

    init {
        viewModelScope.launch {
            tokenRepository.getToken().collect { token ->

                if (!token.isNullOrEmpty()) {
                    _state.update {
                        it.copy(
                            uiMode = SignupScreenUiMode.SUCCESS
                        )
                    }
                }

            }
        }
    }

    fun createUser(fullName: String, username: String, password: String) {
        _state.update {
            it.copy(
                uiMode = SignupScreenUiMode.LOADING
            )
        }
        viewModelScope.launch {
            delay(1000)
            authRepo.createUser(RegisterDTO(fullName, username, password)).fold(
                onSuccess = {
                    tokenRepository.saveToken(it.token)
                    _state.update {
                        it.copy(
                            uiMode = SignupScreenUiMode.SUCCESS
                        )
                    }
                },
                onFailure = { error ->
                    _state.update {
                        it.copy(
                            uiMode = SignupScreenUiMode.ERROR,
                            errorMessage = error.localizedMessage
                        )
                    }
                }
            )
        }
    }
}