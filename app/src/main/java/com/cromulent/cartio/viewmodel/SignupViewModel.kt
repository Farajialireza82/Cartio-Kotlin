package com.cromulent.cartio.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cromulent.cartio.network.dto.RegisterDTO
import com.cromulent.cartio.repository.AuthRepositoryImpl
import kotlinx.coroutines.launch

class SignupViewModel(private val authRepo: AuthRepositoryImpl): ViewModel(){

    fun createUser(fullName: String, username: String, password: String) {
        viewModelScope.launch {
            authRepo.createUser(RegisterDTO(fullName, username, password)).fold(
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