package com.vkartik.myspace.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.vkartik.myspace.MySpaceAppState
import com.vkartik.myspace.ui.presentation.expenses.ExpensesScreen
import com.vkartik.myspace.ui.presentation.expenses.RecordTransactionScreen
import com.vkartik.myspace.ui.presentation.home.HomeScreen
import com.vkartik.myspace.ui.presentation.sign_in.SignInScreen
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
    data object RecordTransactions: Routes()
}


@Composable
fun MySpaceNavHost(appState: MySpaceAppState, startDestination: Routes) {

    NavHost(navController = appState.navController, startDestination = startDestination) {
        composable<Routes.SignIn> {
            SignInScreen {isNewUser ->
                appState.navigateAndPopUp(Routes.Home(isNewUser), Routes.SignIn)
            }
        }

        composable<Routes.Home> { backStackEntry ->
            val customValue = backStackEntry.toRoute<Routes.Home>()
            HomeScreen(appState, newUser = customValue.newUser) {
                appState.navigateAndPopUp(Routes.SignIn, Routes.Home(customValue.newUser))
            }
        }

        composable<Routes.Expenses> {
            ExpensesScreen {
                appState.navigate(Routes.RecordTransactions)
            }
        }
        composable<Routes.RecordTransactions> {
            RecordTransactionScreen()
        }
    }
}