package com.example.core.data.interactors

import com.example.core.data.dao.CategoryDao
import com.example.core.data.entity.CategoryEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CategoryLocalDataSource @Inject constructor(
    private val categoryDao: CategoryDao
) {

    suspend fun insert(category: CategoryEntity) {
        categoryDao.insert(category)
    }

    fun getAllCategories(): Flow<List<CategoryEntity>> {
        return categoryDao.getAll()
    }
}