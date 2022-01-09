package com.specikman.petbest.domain.use_case.history_use_cases

import com.specikman.petbest.common.Resource
import com.specikman.petbest.domain.model.History
import com.specikman.petbest.domain.model.User
import com.specikman.petbest.domain.repository.HistoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AddHistoryUseCase @Inject constructor(
    private val repository: HistoryRepository
) {
    operator fun invoke(history: History): Flow<Resource<String>> = flow {
        try{
            emit(Resource.Loading<String>())
            repository.addHistory(history = history)
            emit(Resource.Success<String>("Thêm thành công"))
        }catch(e: Exception){
            emit(Resource.Error<String>(e.localizedMessage?:"An unexpected error occurred"))
        }
    }
}