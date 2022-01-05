package com.specikman.petbest.domain.repository

import com.specikman.petbest.domain.model.Product

interface ProductRepository {

    suspend fun getProducts(): List<Product>

    suspend fun getBestSellerProducts(): List<Product>

    suspend fun getMostDiscountProducts(): List<Product>

    suspend fun getProductById(id: Int): Product

}