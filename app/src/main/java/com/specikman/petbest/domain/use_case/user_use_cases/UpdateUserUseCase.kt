package com.specikman.petbest.domain.use_case.user_use_cases

import com.specikman.petbest.common.Resource
import com.specikman.petbest.domain.model.User
import com.specikman.petbest.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpdateUserUseCase @Inject constructor(
    private val repository: UserRepository
) {
    operator fun invoke(user: User): Flow<Resource<String>> = flow {
        try{
            emit(Resource.Loading<String>())
            repository.updateUser(user = user)
            emit(Resource.Success<String>("Thêm thành công"))
        }catch(e: Exception){
            emit(Resource.Error<String>(e.localizedMessage?:"An unexpected error occurred"))
        }
    }
}