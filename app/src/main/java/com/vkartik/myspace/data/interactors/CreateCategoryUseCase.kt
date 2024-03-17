package com.vkartik.myspace.data.interactors

import com.vkartik.myspace.domain.Category
import com.vkartik.myspace.domain.repository.CategoryRepository
import javax.inject.Inject

class CreateCategoryUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
) {
    suspend fun execute(category: Category): Boolean {
        return categoryRepository.createCategory(category)
    }
}