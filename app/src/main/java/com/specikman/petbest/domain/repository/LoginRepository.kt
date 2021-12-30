package com.specikman.petbest.domain.repository

interface LoginRepository {
    suspend fun loginWithEmail(
        email: String,
        password: String
    )
}