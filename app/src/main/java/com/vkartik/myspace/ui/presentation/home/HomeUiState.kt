package com.vkartik.myspace.ui.presentation.home

import com.example.core.domain.Category
import com.vkartik.myspace.ui.presentation.sign_in.UserData

data class HomeUiState(
    val userData: UserData,
    val showCategoryDialog: Boolean = false,
    val categoryList: List<Category> = emptyList()
)