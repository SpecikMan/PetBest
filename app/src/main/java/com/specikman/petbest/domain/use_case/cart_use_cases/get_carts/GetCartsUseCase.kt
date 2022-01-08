package com.specikman.petbest.domain.use_case.cart_use_cases.get_carts

import com.specikman.petbest.common.Resource
import com.specikman.petbest.domain.model.Cart
import com.specikman.petbest.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCartsUseCase @Inject constructor(
    private val repository: CartRepository
) {
    operator fun invoke(): Flow<Resource<List<Cart>>> = flow {
        try{
            emit(Resource.Loading<List<Cart>>())
            val carts = repository.getCarts()
            emit(Resource.Success<List<Cart>>(carts))
        }catch(e: Exception){
            emit(Resource.Error<List<Cart>>(e.localizedMessage?:"An unexpected error occurred"))
        }
    }
}