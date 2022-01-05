package com.specikman.petbest.data.repository

import com.specikman.petbest.data.remote.firebase.FirebaseAPI
import com.specikman.petbest.domain.model.Product
import com.specikman.petbest.domain.repository.ProductRepository
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val api: FirebaseAPI
) : ProductRepository{
    override suspend fun getProducts(): List<Product> {
        return api.getProducts()
    }

    override suspend fun getBestSellerProducts(): List<Product> {
        return api.getBestSellerProducts()
    }

    override suspend fun getMostDiscountProducts(): List<Product> {
        return api.getMostDiscountProducts()
    }

    override suspend fun getProductById(id: Int): Product {
        return api.getProductById(id)
    }
}