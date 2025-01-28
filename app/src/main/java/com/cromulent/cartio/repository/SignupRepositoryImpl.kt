package com.cromulent.cartio.repository

import com.cromulent.cartio.data.ShopItem
import com.cromulent.cartio.network.AuthenticationApiService
import com.cromulent.cartio.network.ShopItemApiService
import com.cromulent.cartio.network.dto.RegisterDTO
import com.cromulent.cartio.network.dto.TokenDTO
import io.ktor.client.call.body

class SignupRepositoryImpl(
    private val signupApi: AuthenticationApiService
): SignupRepository {

    override suspend fun createUser(newUser: RegisterDTO): Result<TokenDTO> {
        return runCatching {
            signupApi.createUser(newUser).body()
        }
    }

}