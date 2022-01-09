package com.specikman.petbest.domain.use_case.history_use_cases

import com.specikman.petbest.common.Resource
import com.specikman.petbest.domain.model.History
import com.specikman.petbest.domain.model.User
import com.specikman.petbest.domain.repository.HistoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetHistoryUseCase @Inject constructor(
    private val repository: HistoryRepository
) {
    operator fun invoke(): Flow<Resource<List<History>>> = flow {
        try{
            emit(Resource.Loading<List<History>>())
            val history = repository.getHistory()
            emit(Resource.Success<List<History>>(history))
        }catch(e: Exception){
            emit(Resource.Error<List<History>>(e.localizedMessage?:"An unexpected error occurred"))
        }
    }
}