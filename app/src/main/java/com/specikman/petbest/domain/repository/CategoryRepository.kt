package com.specikman.petbest.domain.repository

import com.specikman.petbest.domain.model.Category

interface CategoryRepository {

    suspend fun getCategories(): List<Category>
}