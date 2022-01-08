package com.specikman.petbest.domain.use_case.product_use_cases.get_products

import com.specikman.petbest.common.Resource
import com.specikman.petbest.domain.model.Product
import com.specikman.petbest.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetBestSellerProductsUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    operator fun invoke(): Flow<Resource<List<Product>>> = flow {
        try {
            emit(Resource.Loading<List<Product>>())
            val products = repository.getBestSellerProducts()
            emit(Resource.Success<List<Product>>(products))
        } catch (e: Exception) {
            emit(Resource.Error<List<Product>>(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }
}