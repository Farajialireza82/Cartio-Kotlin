package com.cromulent.cartio.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class LoginDTO(
    val username: String,
    val password: String
)
