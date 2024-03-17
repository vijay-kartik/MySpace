package com.vkartik.myspace.domain.repository

import com.vkartik.myspace.domain.Category

interface CategoryRepository {
    suspend fun createCategory(category: Category): Boolean
    suspend fun getAllCategories(): List<Category>
}