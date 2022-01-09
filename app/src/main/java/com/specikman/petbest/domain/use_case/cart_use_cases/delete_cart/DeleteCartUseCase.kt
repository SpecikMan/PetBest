package com.specikman.petbest.domain.use_case.cart_use_cases.delete_cart

import com.specikman.petbest.common.Resource
import com.specikman.petbest.domain.model.Cart
import com.specikman.petbest.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DeleteCartUseCase @Inject constructor(
    private val repository: CartRepository
) {
    operator fun invoke(cart: Cart): Flow<Resource<String>> = flow {
        try{
            emit(Resource.Loading<String>())
            repository.deleteCart(cart = cart)
            emit(Resource.Success<String>("Xóa thành công"))
        }catch(e: Exception){
            emit(Resource.Error<String>(e.localizedMessage?:"An unexpected error occurred"))
        }
    }
}