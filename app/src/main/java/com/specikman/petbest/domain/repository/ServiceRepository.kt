package com.specikman.petbest.domain.repository

import com.specikman.petbest.domain.model.Service

interface ServiceRepository {
    suspend fun getServices(): List<Service>
}