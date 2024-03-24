package com.vkartik.myspace.domain

import com.example.core.data.interactors.CategoryRepository
import com.example.core.domain.Category
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(
    private val repository: CategoryRepository
) {
    suspend fun execute(): List<Category> {
        return repository.getAllCategories()
    }
}