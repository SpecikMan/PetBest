package com.specikman.petbest.presentation.main_screen.view_models

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.specikman.petbest.common.Resource
import com.specikman.petbest.domain.model.Product
import com.specikman.petbest.domain.use_case.get_categories.GetCategoriesUseCase
import com.specikman.petbest.domain.use_case.get_products.GetBestSellerProductsUseCase
import com.specikman.petbest.domain.use_case.get_products.GetProductImagesFromStorageUseCase
import com.specikman.petbest.domain.use_case.get_products.GetProductsUseCase
import com.specikman.petbest.presentation.main_screen.state.ImageState
import com.specikman.petbest.presentation.main_screen.state.ProductsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase,
    private val getBestSellerProductsUseCase: GetBestSellerProductsUseCase,
    private val getProductImagesFromStorageUseCase: GetProductImagesFromStorageUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase
) : ViewModel() {

    private val _stateProducts = mutableStateOf(ProductsState())
    val stateProducts: State<ProductsState> = _stateProducts

    private val _stateBestSellerProducts = mutableStateOf(ProductsState())
    val stateBestSellerProducts: State<ProductsState> = _stateBestSellerProducts

    private val _stateImages = mutableStateOf(ImageState())
    val stateImages: State<ImageState> = _stateImages


    init {
        getProducts()
        getBestSellerProducts()
        getProductImagesFromStorage()
    }

    private fun getProducts() {
        getProductsUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _stateProducts.value = ProductsState(products = result.data ?: emptyList())
                }
                is Resource.Loading -> {
                    _stateProducts.value = ProductsState(isLoading = true)
                }
                is Resource.Error -> {
                    _stateProducts.value =
                        ProductsState(error = result.message ?: "An unexpected error occurred")
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getBestSellerProducts() {
        getBestSellerProductsUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _stateBestSellerProducts.value = ProductsState(products = result.data ?: emptyList())
                }
                is Resource.Loading -> {
                    _stateBestSellerProducts.value = ProductsState(isLoading = true)
                }
                is Resource.Error -> {
                    _stateBestSellerProducts.value =
                        ProductsState(error = result.message ?: "An unexpected error occurred")
                }
            }
        }.launchIn(viewModelScope)
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