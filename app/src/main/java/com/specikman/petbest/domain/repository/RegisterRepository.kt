package com.specikman.petbest.domain.repository

interface RegisterRepository {
    suspend fun register(
        email: String,
        password: String,
        phone: String,
        name: String
    )
}