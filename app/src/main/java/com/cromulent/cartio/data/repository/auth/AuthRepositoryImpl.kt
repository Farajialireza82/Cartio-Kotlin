package com.cromulent.cartio.data.repository.auth

import com.cromulent.cartio.data.remote.util.ApiResponse
import com.cromulent.cartio.data.remote.api.AuthenticationApiService
import com.cromulent.cartio.data.remote.dto.LoginDTO
import com.cromulent.cartio.data.remote.dto.RegisterDTO
import com.cromulent.cartio.data.remote.dto.TokenDTO
import com.cromulent.cartio.data.local.TokenRepository

class AuthRepositoryImpl(
    private val authApi: AuthenticationApiService,
    private val tokenRepository: TokenRepository
) : AuthRepository {
    override suspend fun createUser(newUser: RegisterDTO): Result<TokenDTO> =
        authApi.createUser(newUser).toResult()

    override suspend fun userLogin(loginData: LoginDTO): Result<TokenDTO> =
        authApi.userLogin(loginData).toResult()

    private fun <T> ApiResponse<T>.toResult(): Result<T> = when (this) {
        is ApiResponse.Success -> Result.success(data)
        is ApiResponse.Error -> Result.failure(Exception(message))
        is ApiResponse.Exception -> Result.failure(e)
    }
}