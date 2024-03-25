package com.vkartik.myspace.domain

import com.example.core.domain.interactors.CategoryRepository
import com.example.core.domain.Category
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(
    private val repository: CategoryRepository
) {
    suspend fun execute(): Flow<List<Category>> {
        return repository.getAllCategories()
    }
}