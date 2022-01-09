package com.specikman.petbest.domain.use_case.order_use_cases.add_order

import com.specikman.petbest.common.Resource
import com.specikman.petbest.domain.model.Order
import com.specikman.petbest.domain.repository.OrderRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AddOrderUseCase @Inject constructor(
    private val repository: OrderRepository
) {
    operator fun invoke(order: Order): Flow<Resource<String>> = flow {
        try{
            emit(Resource.Loading<String>())
            repository.addOrder(order = order)
            emit(Resource.Success<String>("Thêm thành công"))
        }catch(e: Exception){
            emit(Resource.Error<String>(e.localizedMessage?:"An unexpected error occurred"))
        }
    }
}