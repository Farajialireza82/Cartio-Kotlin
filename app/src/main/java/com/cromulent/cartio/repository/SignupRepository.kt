package com.cromulent.cartio.repository

import com.cromulent.cartio.network.dto.RegisterDTO
import com.cromulent.cartio.network.dto.TokenDTO

interface SignupRepository {
    suspend fun createUser(newUser: RegisterDTO): Result<TokenDTO>
}