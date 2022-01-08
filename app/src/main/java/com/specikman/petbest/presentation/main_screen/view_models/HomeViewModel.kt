package com.specikman.petbest.presentation.main_screen.view_models

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.specikman.petbest.common.Resource
import com.specikman.petbest.domain.model.Cart
import com.specikman.petbest.domain.model.Favorite
import com.specikman.petbest.domain.model.Product
import com.specikman.petbest.domain.use_case.cart_use_cases.add_cart.AddCartUseCase
import com.specikman.petbest.domain.use_case.cart_use_cases.get_carts.GetCartsUseCase
import com.specikman.petbest.domain.use_case.cart_use_cases.update_cart.UpdateCartUseCase
import com.specikman.petbest.domain.use_case.product_use_cases.favorite.AddFavoriteUseCase
import com.specikman.petbest.domain.use_case.product_use_cases.favorite.GetFavoriteUseCase
import com.specikman.petbest.domain.use_case.product_use_cases.get_products.GetBestSellerProductsUseCase
import com.specikman.petbest.domain.use_case.product_use_cases.get_products.GetMostDiscountProductsUseCase
import com.specikman.petbest.domain.use_case.product_use_cases.get_products.GetProductImagesFromStorageUseCase
import com.specikman.petbest.domain.use_case.product_use_cases.get_products.GetProductsUseCase
import com.specikman.petbest.presentation.main_screen.state.CartsState
import com.specikman.petbest.presentation.main_screen.state.FavoriteState
import com.specikman.petbest.presentation.main_screen.state.ImageState
import com.specikman.petbest.presentation.main_screen.state.ProductsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase,
    private val getBestSellerProductsUseCase: GetBestSellerProductsUseCase,
    private val getProductImagesFromStorageUseCase: GetProductImagesFromStorageUseCase,
    private val getMostDiscountProductsUseCase: GetMostDiscountProductsUseCase,
    private val getCartsUseCase: GetCartsUseCase,
    private val addCartUseCase: AddCartUseCase,
    private val updateCartUseCase: UpdateCartUseCase,
    private val addFavoriteUseCase: AddFavoriteUseCase,
    private val getFavoriteUseCase: GetFavoriteUseCase
) : ViewModel() {

    private val _stateProducts = mutableStateOf(ProductsState())
    val stateProducts: State<ProductsState> = _stateProducts

    private val _stateBestSellerProducts = mutableStateOf(ProductsState())
    val stateBestSellerProducts: State<ProductsState> = _stateBestSellerProducts

    private val _stateMostDiscountProducts = mutableStateOf(ProductsState())
    val stateMostDiscountProducts: State<ProductsState> = _stateMostDiscountProducts

    private val _stateImages = mutableStateOf(ImageState())
    val stateImages: State<ImageState> = _stateImages

    val _stateShowProduct = mutableStateOf(_stateProducts.value.products)
    var stateShowProduct: State<List<Product>> = _stateShowProduct

    val _stateProductDetail = mutableStateOf(Product())
    var stateProductDetail: State<Product> = _stateProductDetail

    private val _stateCarts = mutableStateOf(CartsState())
    val stateCarts: State<CartsState> = _stateCarts

    private val _stateUpdateCart = mutableStateOf("")
    val stateUpdateCart: State<String> = _stateUpdateCart

    private val _stateFavorite = mutableStateOf(FavoriteState())
    val stateFavorite: State<FavoriteState> = _stateFavorite

    private val _stateFavoriteList = mutableStateOf(emptyList<Favorite>())
    val stateFavoriteList: State<List<Favorite>> = _stateFavoriteList

    val _stateFloatingButton = mutableStateOf(true)
    var stateFloatingButton: State<Boolean> = _stateFloatingButton

    init {
        getProducts()
        getBestSellerProducts()
        getMostDiscountProducts()
        getProductImagesFromStorage()
        getCarts()
        getFavorite()
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
                    _stateBestSellerProducts.value =
                        ProductsState(products = result.data ?: emptyList())
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

    private fun getMostDiscountProducts() {
        getMostDiscountProductsUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _stateMostDiscountProducts.value =
                        ProductsState(products = result.data ?: emptyList())
                }
                is Resource.Loading -> {
                    _stateMostDiscountProducts.value = ProductsState(isLoading = true)
                }
                is Resource.Error -> {
                    _stateMostDiscountProducts.value =
                        ProductsState(error = result.message ?: "An unexpected error occurred")
                }
            }
        }.launchIn(viewModelScope)
    }


    //Cart
    fun getCarts() {
        getCartsUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _stateCarts.value = CartsState(carts = result.data ?: emptyList())
                }
                is Resource.Loading -> {
                    _stateCarts.value = CartsState(isLoading = true)
                }
                is Resource.Error -> {
                    _stateCarts.value =
                        CartsState(error = result.message ?: "An unexpected error occurred")
                }
            }
        }.launchIn(viewModelScope)
    }

    fun addCart(cart: Cart) {
        addCartUseCase(cart).onEach { result ->
            when (result) {
                is Resource.Success -> {
                }
                is Resource.Loading -> {
                }
                is Resource.Error -> {
                }
            }
        }.launchIn(viewModelScope)
    }

    fun updateCart(cart: Cart) {
        updateCartUseCase(cart).onEach { result ->
            when (result) {
                is Resource.Success -> {
                }
                is Resource.Loading -> {
                }
                is Resource.Error -> {
                }
            }
        }.launchIn(viewModelScope)
    }

    fun addFavorite(favorite: Favorite) = addFavoriteUseCase(favorite).onEach { result ->
        when (result) {
            is Resource.Success -> {
                _stateFavorite.value = FavoriteState(isLike = result.data ?: false)
            }
            is Resource.Loading -> {
                _stateFavorite.value = FavoriteState(isLoading = true)
            }
            is Resource.Error -> {
                _stateFavorite.value = FavoriteState(error = "Error")
            }
        }
    }.launchIn(viewModelScope)


    fun getFavorite() {
        getFavoriteUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _stateFavoriteList.value = result.data ?: emptyList<Favorite>()
                }
                is Resource.Loading -> {
                    _stateFavoriteList.value = emptyList<Favorite>()
                }
                is Resource.Error -> {
                    _stateFavoriteList.value =
                        emptyList<Favorite>()
                }
            }
        }.launchIn(viewModelScope)
    }
}

