package com.specikman.petbest.presentation.main_screen.view_models

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.specikman.petbest.common.Resource
import com.specikman.petbest.domain.use_case.get_categories.GetCategoriesUseCase
import com.specikman.petbest.domain.use_case.get_products.GetProductsUseCase
import com.specikman.petbest.presentation.main_screen.state.ProductsState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase
): ViewModel() {
    private val _state = mutableStateOf(ProductsState())
    val state: State<ProductsState> = _state

    init {
        getProducts()
    }

    private fun getProducts(){
        getProductsUseCase().onEach { result ->
            when(result){
                is Resource.Success -> {
                    _state.value = ProductsState(products = result.data ?: emptyList())
                }
                is Resource.Loading -> {
                    _state.value = ProductsState(isLoading = true)
                }
                is Resource.Error -> {
                    _state.value = ProductsState(error = result.message ?: "An unexpected error occurred")
                }
            }
        }.launchIn(viewModelScope)
    }
}