package com.specikman.petbest.presentation.main_screen.state

import com.specikman.petbest.domain.model.Product

data class ProductsState(
    val isLoading: Boolean = false,
    val products: List<Product> = emptyList(),
    val error: String = ""
)