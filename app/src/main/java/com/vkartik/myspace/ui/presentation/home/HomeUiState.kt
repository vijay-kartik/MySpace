package com.vkartik.myspace.ui.presentation.home

import com.example.core.domain.Category

data class HomeUiState(
    val showCategoryDialog: Boolean = false,
    val categoryList: List<Category> = emptyList()
)