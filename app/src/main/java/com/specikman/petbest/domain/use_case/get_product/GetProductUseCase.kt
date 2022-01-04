package com.specikman.petbest.domain.use_case.get_product

import com.specikman.petbest.common.Resource
import com.specikman.petbest.domain.model.Category
import com.specikman.petbest.domain.model.Product
import com.specikman.petbest.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetProductUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    operator fun invoke(productId: Int): Flow<Resource<Product>> = flow{
        try{
            emit(Resource.Loading<Product>())
            val product = repository.getProductById(productId)
            emit(Resource.Success<Product>(product))
        }catch(e: Exception){
            emit(Resource.Error<Product>(e.localizedMessage?:"An unexpected error occurred"))
        }
    }
}