package com.cromulent.cartio.repository

import com.cromulent.cartio.network.AuthenticationApiService
import com.cromulent.cartio.network.dto.LoginDTO
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

    override suspend fun userLogin(loginData: LoginDTO): Result<TokenDTO> {
        return runCatching {
            signupApi.userLogin(loginData).body()
        }
    }

}