package com.specikman.petbest.presentation.main_screen.state

import com.specikman.petbest.data.remote.dto.Image

data class ImageState(
    val isLoading: Boolean = false,
    val images: List<Image> = emptyList(),
    val error: String = ""
)