package com.specikman.petbest.data.repository

import com.specikman.petbest.data.remote.firebase.FirebaseAPI
import com.specikman.petbest.domain.model.User
import com.specikman.petbest.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val api: FirebaseAPI
): UserRepository {
    override suspend fun getUsers(): List<User> {
        return api.getUsers()
    }

    override suspend fun updateUser(user: User) {
        api.updateUser(user)
    }
}