package com.vkartik.myspace.data.interactors

import com.example.core.data.interactors.CategoryRepository
import com.example.core.domain.Category
import javax.inject.Inject

class CreateCategoryUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
) {
    suspend fun execute(category: Category): Boolean {
        return categoryRepository.createCategory(category)
    }
}