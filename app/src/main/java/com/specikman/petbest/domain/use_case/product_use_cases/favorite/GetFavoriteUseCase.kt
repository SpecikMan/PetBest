package com.specikman.petbest.domain.use_case.product_use_cases.favorite

import com.specikman.petbest.common.Resource
import com.specikman.petbest.domain.model.Favorite
import com.specikman.petbest.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetFavoriteUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    operator fun invoke(): Flow<Resource<List<Favorite>>> = flow {
        try{
            emit(Resource.Loading<List<Favorite>>())
            val bool = repository.getFavoriteProducts()
            emit(Resource.Success<List<Favorite>>(bool))
        }catch(e: Exception){
            emit(Resource.Error<List<Favorite>>(e.localizedMessage?:"An unexpected error occurred"))
        }
    }
}