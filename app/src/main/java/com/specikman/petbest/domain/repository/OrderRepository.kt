package com.specikman.petbest.domain.repository

import com.specikman.petbest.domain.model.Order

interface OrderRepository {
    suspend fun getOrders(): List<Order>

    suspend fun addOrder(order: Order)
}