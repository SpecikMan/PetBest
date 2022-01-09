package com.specikman.petbest.domain.repository

import com.specikman.petbest.domain.model.Cart

interface CartRepository {

    suspend fun getCarts(): List<Cart>

    suspend fun addCart(cart: Cart)

    suspend fun updateCart(cart: Cart)

    suspend fun deleteCart(cart: Cart)
}