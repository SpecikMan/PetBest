package com.specikman.petbest.data.repository

import com.specikman.petbest.data.remote.firebase.FirebaseAPI
import com.specikman.petbest.domain.model.Order
import com.specikman.petbest.domain.repository.OrderRepository
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(
    private val api: FirebaseAPI
): OrderRepository{
    override suspend fun getOrders(): List<Order> {
        return api.getOrders()
    }

    override suspend fun addOrder(order: Order) {
        api.addOrder(order)
    }
}