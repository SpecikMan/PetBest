package com.specikman.petbest.data.repository

import com.specikman.petbest.data.remote.firebase.FirebaseAPI
import com.specikman.petbest.domain.repository.RegisterRepository
import javax.inject.Inject

class RegisterRepositoryImpl @Inject constructor(
    private val api: FirebaseAPI
) : RegisterRepository {
    override suspend fun register(email: String, password: String, phone: String, name: String) {
        api.register(email = email, password = password, phone = phone, name = name)
    }
}