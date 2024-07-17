package com.vkartik.myspace.ui.presentation.expenses

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.vkartik.myspace.ui.presentation.expenses.components.ExpensesActionCard

@Composable
fun ExpensesScreen(
    modifier: Modifier = Modifier,
    viewModel: ExpensesViewModel = hiltViewModel(),
    navigateToRecordExpenses: () -> Unit
) {
    val actionList: List<String> = viewModel.getActionCardList()
    LazyVerticalGrid(
        modifier = modifier.fillMaxSize().padding(10.dp),
        columns = GridCells.Adaptive(minSize = 130.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        items(actionList.size) {index ->
            ExpensesActionCard(
                modifier = Modifier.size(150.dp),
                title = actionList[index]
            ) { navigateToRecordExpenses() }
        }
    }
}

@Composable
@Preview
fun ExpensesScreenPreview() {
    val actionList: List<String> = listOf("A", "B")

    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize().padding(10.dp),
        columns = GridCells.Adaptive(minSize = 120.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        items(actionList.size) {index ->
            ExpensesActionCard(
                modifier = Modifier.size(150.dp),
                title = actionList[index]
            ) { }
        }
    }
}