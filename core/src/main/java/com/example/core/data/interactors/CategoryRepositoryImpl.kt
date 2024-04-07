package com.example.core.data.interactors

import com.example.core.data.source.CategoryRemoteDataSource
import com.example.core.data.toDomain
import com.example.core.data.toEntity
import com.example.core.domain.Category
import com.example.core.domain.interactors.CategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val localDataSource: CategoryLocalDataSource,
    private val remoteDataSource: CategoryRemoteDataSource
): CategoryRepository {
    override suspend fun createCategory(category: Category): Boolean {
        try {
            val created = remoteDataSource.createCategory(category)
            if (created) {
                localDataSource.insert(category.toEntity())
                return true
            } else return false
        } catch (e: Exception) {
            return false
        }
    }

    override suspend fun getAllCategories(newUser: Boolean): Flow<List<Category>> {
        if (newUser) {
            val categories = remoteDataSource.getAllCategories()
            localDataSource.insertAll(categories.map { it.toEntity() })
        }
        return localDataSource.getAllCategories().map { categories ->
            categories.map { it.toDomain() }
        }
    }

    override suspend fun clearData() {
        localDataSource.deleteAll()
    }
}