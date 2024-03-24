package com.example.core.data.interactors

import com.example.core.domain.Category

interface CategoryRepository {
    suspend fun createCategory(category: Category): Boolean
    suspend fun getAllCategories(): List<Category>
}