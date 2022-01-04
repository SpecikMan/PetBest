package com.specikman.petbest.data.repository

import com.specikman.petbest.data.remote.firebase.FirebaseAPI
import com.specikman.petbest.domain.model.Category
import com.specikman.petbest.domain.repository.CategoryRepository
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val api: FirebaseAPI
): CategoryRepository{
    override suspend fun getCategories(): List<Category> {
        return api.getCategories()
    }
}