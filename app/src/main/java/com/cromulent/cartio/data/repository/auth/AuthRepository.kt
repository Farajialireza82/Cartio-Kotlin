package com.cromulent.cartio.data.repository.auth

import com.cromulent.cartio.data.remote.dto.LoginDTO
import com.cromulent.cartio.data.remote.dto.RegisterDTO
import com.cromulent.cartio.data.remote.dto.TokenDTO

interface AuthRepository {
    suspend fun createUser(newUser: RegisterDTO): Result<TokenDTO>

    suspend fun userLogin(loginData: LoginDTO): Result<TokenDTO>
}