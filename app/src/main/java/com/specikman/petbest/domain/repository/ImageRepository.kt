package com.specikman.petbest.domain.repository

import com.specikman.petbest.data.remote.dto.Image
import com.specikman.petbest.domain.model.Product

interface ImageRepository {
    suspend fun getProductImagesFromStorage(): List<Image>
}