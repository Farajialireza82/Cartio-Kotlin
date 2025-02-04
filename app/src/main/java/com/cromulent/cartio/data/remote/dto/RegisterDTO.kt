package com.cromulent.cartio.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class RegisterDTO(
    val fullName: String,
    val username: String,
    val password: String
)
