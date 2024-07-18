package com.vkartik.myspace.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.vkartik.myspace.MySpaceAppState
import com.vkartik.myspace.ui.presentation.home.HomeScreen
import com.vkartik.myspace.ui.presentation.sign_in.SignInScreen

fun NavGraphBuilder.mainGraph(appState: MySpaceAppState) {
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

    navigation<Routes.Expenses>(startDestination = Routes.ExpensesDashBoard) {
        expensesGraph(appState = appState)
    }
}