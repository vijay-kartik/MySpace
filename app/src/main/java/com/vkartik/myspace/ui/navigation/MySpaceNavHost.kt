package com.vkartik.myspace.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import com.vkartik.myspace.MySpaceAppState
import kotlinx.serialization.Serializable

@Serializable
sealed class Routes {
    @Serializable
    data class Home(val newUser: Boolean): Routes()

    @Serializable
    data object SignIn: Routes()

    @Serializable
    data object Expenses: Routes()

    @Serializable
    data object ExpensesDashBoard: Routes()

    @Serializable
    data object RecordTransactions: Routes()
}


@Composable
fun MySpaceNavHost(appState: MySpaceAppState, startDestination: Routes) {
    NavHost(navController = appState.navController, startDestination = startDestination) {
        mainGraph(appState = appState)
    }
}