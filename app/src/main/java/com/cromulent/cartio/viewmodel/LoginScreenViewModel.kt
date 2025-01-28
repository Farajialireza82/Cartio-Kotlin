package com.cromulent.cartio.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cromulent.cartio.network.dto.LoginDTO
import com.cromulent.cartio.network.dto.RegisterDTO
import com.cromulent.cartio.repository.AuthRepositoryImpl
import kotlinx.coroutines.launch

class LoginScreenViewModel(private val authRepo: AuthRepositoryImpl) : ViewModel() {

    fun userLogin(username: String, password: String) {
        viewModelScope.launch {
            authRepo.userLogin(LoginDTO(username, password)).fold(
                onSuccess = {
                    //login
                },
                onFailure = {
                    //Show Error
                }
            )
        }
    }
}