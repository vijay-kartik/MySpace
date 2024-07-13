package com.vkartik.myspace.ui.presentation.expenses

data class ExpenseUiState(
    val currentScreen: ExpenseState = ExpenseState.MAIN
)

enum class ExpenseState {
    MAIN, CREATE, SETTINGS
}