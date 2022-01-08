package com.specikman.petbest.domain.use_case.product_use_cases.favorite

import com.specikman.petbest.common.Resource
import com.specikman.petbest.domain.model.Favorite
import com.specikman.petbest.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AddFavoriteUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    operator fun invoke(favorite: Favorite): Flow<Resource<Boolean>> = flow {
        try{
            emit(Resource.Loading<Boolean>())
            val bool = repository.addProductToFavorite(favorite = favorite)
            emit(Resource.Success<Boolean>(bool))
        }catch(e: Exception){
            emit(Resource.Error<Boolean>(e.localizedMessage?:"An unexpected error occurred"))
        }
    }
}