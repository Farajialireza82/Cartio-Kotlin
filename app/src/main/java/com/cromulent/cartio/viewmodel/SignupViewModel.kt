package com.cromulent.cartio.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cromulent.cartio.network.dto.RegisterDTO
import com.cromulent.cartio.repository.AuthRepositoryImpl
import com.cromulent.cartio.state.LoginScreenState
import com.cromulent.cartio.state.LoginScreenUiMode
import com.cromulent.cartio.utils.TokenRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.security.KeyStore

class SignupViewModel(private val authRepo: AuthRepositoryImpl, private val tokenRepository: TokenRepository): ViewModel(){
    private val _state: MutableStateFlow<LoginScreenState> = MutableStateFlow(LoginScreenState())
    val state: StateFlow<LoginScreenState> = _state

    init {
        viewModelScope.launch {
            tokenRepository.getToken().collect { token ->

                if(!token.isNullOrEmpty()){
                    _state.update {
                        it.copy(
                            uiMode = LoginScreenUiMode.SUCCESS
                        )
                    }
                }

            }
        }
    }

    fun createUser(fullName: String, username: String, password: String) {
        viewModelScope.launch {
            authRepo.createUser(RegisterDTO(fullName, username, password)).fold(
                onSuccess = {
                    tokenRepository.saveToken(it.token)
                    _state.update {
                        it.copy(
                            uiMode = LoginScreenUiMode.SUCCESS
                        )
                    }
                },
                onFailure = {
                    //Show Error
                }
            )
        }
    }
}