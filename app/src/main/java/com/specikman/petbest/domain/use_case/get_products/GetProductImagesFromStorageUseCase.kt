package com.specikman.petbest.domain.use_case.get_products

import android.graphics.Bitmap
import com.specikman.petbest.common.Resource
import com.specikman.petbest.data.remote.dto.Image
import com.specikman.petbest.domain.model.Product
import com.specikman.petbest.domain.repository.ImageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetProductImagesFromStorageUseCase @Inject constructor(
    private val repository: ImageRepository
) {
    operator fun invoke(): Flow<Resource<List<Image>>> = flow {
        try {
            emit(Resource.Loading<List<Image>>())
            val images = repository.getProductImagesFromStorage()
            emit(Resource.Success<List<Image>>(images))
        } catch (e: Exception) {
            emit(Resource.Error<List<Image>>(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }
}