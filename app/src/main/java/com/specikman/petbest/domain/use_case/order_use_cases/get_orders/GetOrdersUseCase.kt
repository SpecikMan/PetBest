package com.specikman.petbest.domain.use_case.order_use_cases.get_orders

import com.specikman.petbest.common.Resource
import com.specikman.petbest.domain.model.Order
import com.specikman.petbest.domain.repository.OrderRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetOrdersUseCase @Inject constructor(
    private val repository: OrderRepository
) {
    operator fun invoke(): Flow<Resource<List<Order>>> = flow {
        try{
            emit(Resource.Loading<List<Order>>())
            val orders = repository.getOrders()
            emit(Resource.Success<List<Order>>(orders))
        }catch(e: Exception){
            emit(Resource.Error<List<Order>>(e.localizedMessage?:"An unexpected error occurred"))
        }
    }
}