package com.specikman.petbest.presentation.main_screen.state

import com.specikman.petbest.domain.model.Order

data class OrdersState(
    val isLoading: Boolean = false,
    var orders: List<Order> = emptyList(),
    val error: String = ""
)