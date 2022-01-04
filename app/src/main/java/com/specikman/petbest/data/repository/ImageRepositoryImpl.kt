package com.specikman.petbest.data.repository

import android.graphics.Bitmap
import com.specikman.petbest.data.remote.dto.Image
import com.specikman.petbest.data.remote.firebase.FirebaseAPI
import com.specikman.petbest.domain.model.Product
import com.specikman.petbest.domain.repository.ImageRepository
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(
    private val api: FirebaseAPI
): ImageRepository {
    override suspend fun getProductImagesFromStorage(): List<Image> {
        return api.getProductImagesFromStorage()
    }
}