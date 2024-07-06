package com.vkartik.myspace.ui.presentation.home

import com.example.core.domain.Category
import com.vkartik.myspace.ui.presentation.Screens
import com.vkartik.myspace.ui.presentation.SubScreens

data class HomeUiState(
    val showCategoryDialog: Boolean = false,
    val categoryList: List<Category> = emptyList(),
    val currentScreen: SubScreens = SubScreens.DEFAULT
)