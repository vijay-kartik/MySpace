package com.example.core.domain.interactors

import com.example.core.domain.Category
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    suspend fun createCategory(category: Category): Boolean
    suspend fun getAllCategories(): Flow<List<Category>>
}