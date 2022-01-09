package com.specikman.petbest.domain.use_case.user_use_cases

import com.specikman.petbest.common.Resource
import com.specikman.petbest.domain.model.Order
import com.specikman.petbest.domain.model.User
import com.specikman.petbest.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(
    private val repository: UserRepository
) {
    operator fun invoke(): Flow<Resource<List<User>>> = flow {
        try{
            emit(Resource.Loading<List<User>>())
            val users = repository.getUsers()
            emit(Resource.Success<List<User>>(users))
        }catch(e: Exception){
            emit(Resource.Error<List<User>>(e.localizedMessage?:"An unexpected error occurred"))
        }
    }
}