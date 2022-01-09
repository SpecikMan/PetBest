package com.specikman.petbest.domain.use_case.get_services

import com.specikman.petbest.common.Resource
import com.specikman.petbest.domain.model.Service
import com.specikman.petbest.domain.repository.ServiceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetServicesUseCase @Inject constructor(
    private val repository: ServiceRepository
) {
    operator fun invoke(): Flow<Resource<List<Service>>> = flow {
        try{
            emit(Resource.Loading<List<Service>>())
            val services = repository.getServices()
            emit(Resource.Success<List<Service>>(services))
        }catch(e: Exception){
            emit(Resource.Error<List<Service>>(e.localizedMessage?:"An unexpected error occurred"))
        }
    }
}