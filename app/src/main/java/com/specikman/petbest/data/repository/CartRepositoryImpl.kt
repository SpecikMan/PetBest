package com.specikman.petbest.data.repository

import com.specikman.petbest.data.remote.firebase.FirebaseAPI
import com.specikman.petbest.domain.model.Cart
import com.specikman.petbest.domain.repository.CartRepository
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(
    private val api: FirebaseAPI
) : CartRepository {
    override suspend fun getCarts(): List<Cart> {
        return api.getCarts()
    }

    override suspend fun addCart(cart: Cart) {
        api.addCart(cart = cart)
    }

    override suspend fun updateCart(cart: Cart) {
        api.updateCart(cart = cart)
    }
}