package com.cromulent.cartio.repository

import com.cromulent.cartio.network.dto.LoginDTO
import com.cromulent.cartio.network.dto.RegisterDTO
import com.cromulent.cartio.network.dto.TokenDTO

interface AuthRepository {
    suspend fun createUser(newUser: RegisterDTO): Result<TokenDTO>

    suspend fun userLogin(loginData: LoginDTO): Result<TokenDTO>
}