package com.specikman.petbest.presentation.main_screen.state

import com.specikman.petbest.domain.model.Cart

data class CartsState(
    val isLoading: Boolean = false,
    val carts: List<Cart> = emptyList(),
    val error: String = ""
)