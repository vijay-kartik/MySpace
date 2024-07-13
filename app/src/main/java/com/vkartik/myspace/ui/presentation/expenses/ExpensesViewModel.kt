package com.vkartik.myspace.ui.presentation.expenses

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class ExpensesViewModel @Inject constructor(): ViewModel() {
    val expenseUiState: MutableStateFlow<ExpenseUiState> = MutableStateFlow(ExpenseUiState())

}
