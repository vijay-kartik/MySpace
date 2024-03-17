package com.vkartik.myspace.domain

import com.vkartik.myspace.domain.repository.CategoryRepository
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(
    private val repository: CategoryRepository
) {
    suspend fun execute(): List<Category> {
        return repository.getAllCategories()
    }
}