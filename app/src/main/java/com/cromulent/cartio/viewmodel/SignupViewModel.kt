package com.cromulent.cartio.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cromulent.cartio.network.dto.RegisterDTO
import com.cromulent.cartio.repository.SignupRepositoryImpl
import kotlinx.coroutines.launch

class SignupViewModel(private val signupRepo: SignupRepositoryImpl): ViewModel(){

    fun createUser(fullName: String, username: String, password: String) {
        viewModelScope.launch {
            signupRepo.createUser(RegisterDTO(fullName, username, password)).fold(
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