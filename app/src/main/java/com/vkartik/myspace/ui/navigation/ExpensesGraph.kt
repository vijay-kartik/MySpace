package com.vkartik.myspace.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.vkartik.myspace.MySpaceAppState
import com.vkartik.myspace.ui.presentation.expenses.ExpensesScreen
import com.vkartik.myspace.ui.presentation.expenses.RecordTransactionScreen

fun NavGraphBuilder.expensesGraph(appState: MySpaceAppState) {
    composable<Routes.ExpensesDashBoard> {
        ExpensesScreen {
            appState.navigate(Routes.RecordTransactions)
        }
    }
    composable<Routes.RecordTransactions> {
        RecordTransactionScreen()
    }
}