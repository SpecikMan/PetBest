package com.specikman.petbest.presentation.main_screen.state

import com.specikman.petbest.domain.model.Product

data class FavoriteState(
    val isLoading: Boolean = false,
    val isLike: Boolean = false,
    val error: String = ""
)