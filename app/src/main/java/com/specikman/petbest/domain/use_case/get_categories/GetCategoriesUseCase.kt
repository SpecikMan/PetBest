package com.specikman.petbest.domain.use_case.get_categories

import com.specikman.petbest.common.Resource
import com.specikman.petbest.domain.model.Category
import com.specikman.petbest.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(
    private val repository: CategoryRepository
) {
    operator fun invoke(): Flow<Resource<List<Category>>> = flow {
        try{
            emit(Resource.Loading<List<Category>>())
            val cats = repository.getCategories()
            emit(Resource.Success<List<Category>>(cats))
        }catch(e: Exception){
            emit(Resource.Error<List<Category>>(e.localizedMessage?:"An unexpected error occurred"))
        }
    }
}