package com.vkartik.myspace.ui.presentation.home

import com.vkartik.myspace.ui.presentation.sign_in.UserData

data class HomeUiState(
    val userData: UserData,
    val showCategoryDialog: Boolean = false
)