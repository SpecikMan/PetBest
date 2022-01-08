package com.specikman.petbest.domain.use_case.login_use_cases.login

import com.specikman.petbest.common.Resource
import com.specikman.petbest.domain.repository.LoginRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginWithEmailUseCase @Inject constructor(
    private val repository: LoginRepository
) {
    operator fun invoke(email: String, password: String): Flow<Resource<String>> = flow {
        try {
            emit(Resource.Loading<String>())
            repository.loginWithEmail(email = email, password = password)
            emit(Resource.Success<String>("Logged in"))
        } catch (e: Exception) {
            emit(Resource.Error<String>(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }
}