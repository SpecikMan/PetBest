package com.specikman.petbest.domain.repository

import com.specikman.petbest.domain.model.User

interface UserRepository {
    suspend fun getUsers(): List<User>

    suspend fun updateUser(user: User)
}