package com.specikman.petbest.domain.use_case.register

import com.specikman.petbest.common.Resource
import com.specikman.petbest.domain.repository.RegisterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val repository: RegisterRepository
) {
    operator fun invoke(
        email: String,
        password: String,
        phone: String,
        name: String
    ) : Flow<Resource<String>> = flow {
        try {
            emit(Resource.Loading<String>())
            repository.register(email = email, password = password , phone = phone, name = name)
            emit(Resource.Success<String>("Logged in"))
        } catch (e: Exception) {
            emit(Resource.Error<String>(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }
}