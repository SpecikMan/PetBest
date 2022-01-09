package com.specikman.petbest.presentation.main_screen.view_models

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.specikman.petbest.common.Resource
import com.specikman.petbest.domain.model.Order
import com.specikman.petbest.domain.model.Product
import com.specikman.petbest.domain.use_case.product_use_cases.get_products.GetProductImagesFromStorageUseCase
import com.specikman.petbest.presentation.main_screen.state.ImageState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ImageViewModel @Inject constructor(
    private val getProductImagesFromStorageUseCase: GetProductImagesFromStorageUseCase
): ViewModel() {
    private val _stateImages = mutableStateOf(ImageState())
    val stateImages: State<ImageState> = _stateImages

    val _stateFloatingButton = mutableStateOf(true)
    var stateFloatingButton: State<Boolean> = _stateFloatingButton

    val _stateProductDetail = mutableStateOf(Product())
    var stateProductDetail: State<Product> = _stateProductDetail

    val _stateOrderDetail = mutableStateOf(Order())
    var stateOrderDetail: State<Order> = _stateOrderDetail

    val _stateShowProduct = mutableStateOf(emptyList<Product>())
    var stateShowProduct: State<List<Product>> = _stateShowProduct

    init {
        getProductImagesFromStorage()
    }

    private fun getProductImagesFromStorage() {
        getProductImagesFromStorageUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _stateImages.value = ImageState(images = result.data ?: emptyList())
                }
                is Resource.Loading -> {
                    _stateImages.value = ImageState(isLoading = true)
                }
                is Resource.Error -> {
                    _stateImages.value =
                        ImageState(error = result.message ?: "An unexpected error occurred")
                }
            }
        }.launchIn(viewModelScope)
    }
}