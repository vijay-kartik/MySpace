package com.example.core.domain.interactors

import com.example.core.domain.Category
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    suspend fun createCategory(category: Category): Boolean
    suspend fun getAllCategories(newUser: Boolean): Flow<List<Category>>
    suspend fun clearData()
}