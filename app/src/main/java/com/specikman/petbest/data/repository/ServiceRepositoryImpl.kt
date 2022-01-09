package com.specikman.petbest.data.repository

import com.specikman.petbest.data.remote.firebase.FirebaseAPI
import com.specikman.petbest.domain.model.Service
import com.specikman.petbest.domain.repository.ServiceRepository
import javax.inject.Inject

class ServiceRepositoryImpl @Inject constructor(
    private val api: FirebaseAPI
): ServiceRepository {
    override suspend fun getServices(): List<Service> {
        return api.getServices()
    }
}