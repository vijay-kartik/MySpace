package com.vkartik.myspace.ui.presentation.expenses

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.domain.interactors.usecases.GetPromptResponseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExpensesViewModel @Inject constructor(private val promptResponseUseCase: GetPromptResponseUseCase): ViewModel() {
    val expenseUiState: MutableStateFlow<ExpenseUiState> = MutableStateFlow(ExpenseUiState())
    val promptResponse: MutableStateFlow<String?> = MutableStateFlow(null)

    fun obtainResponse(prompt: String) {
        viewModelScope.launch {
           val response = promptResponseUseCase.execute(prompt)
           promptResponse.emit(response)
        }
    }

}
