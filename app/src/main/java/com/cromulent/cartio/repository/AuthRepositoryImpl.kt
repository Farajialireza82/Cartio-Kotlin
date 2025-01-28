package com.cromulent.cartio.repository

import com.cromulent.cartio.network.AuthenticationApiService
import com.cromulent.cartio.network.dto.RegisterDTO
import com.cromulent.cartio.network.dto.TokenDTO
import io.ktor.client.call.body

class AuthRepositoryImpl(
    private val signupApi: AuthenticationApiService
): AuthRepository {

    override suspend fun createUser(newUser: RegisterDTO): Result<TokenDTO> {
        return runCatching {
            signupApi.createUser(newUser).body()
        }
    }

}