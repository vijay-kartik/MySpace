package com.vkartik.myspace.ui.presentation.expenses

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.domain.interactors.usecases.GetPromptResponseUseCase
import com.vkartik.myspace.data.ExpensesActionsDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExpensesViewModel @Inject constructor(): ViewModel() {
    fun getActionCardList(): List<String> = ExpensesActionsDataSource.actionList
}
