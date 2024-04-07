package com.vkartik.myspace.domain

import com.example.core.domain.interactors.CategoryRepository
import javax.inject.Inject

class ClearUserDataUseCase @Inject constructor(
    private val repository: CategoryRepository
) {
    suspend fun execute() {
        repository.clearData()
    }
}