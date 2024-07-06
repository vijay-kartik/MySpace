package com.vkartik.myspace.ui.presentation.expenses

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun ExpensesScreen(viewModel: ExpensesViewModel = hiltViewModel()) {
    Text("Total Expense")
}