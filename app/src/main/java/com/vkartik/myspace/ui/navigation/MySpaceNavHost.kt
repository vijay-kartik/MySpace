package com.vkartik.myspace.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.vkartik.myspace.MySpaceAppState
import com.vkartik.myspace.ui.presentation.Screens
import com.vkartik.myspace.ui.presentation.home.HomeScreen
import com.vkartik.myspace.ui.presentation.sign_in.SignInScreen

@Composable
fun MySpaceNavHost(appState: MySpaceAppState, startDestination: String) {
    NavHost(navController = appState.navController, startDestination = startDestination) {
        composable(route = Screens.SIGN_IN.route) {
            SignInScreen {isNewUser ->
                appState.navigateAndPopUp("home?newUser=$isNewUser", Screens.SIGN_IN.route)
            }
        }

        composable(route = "home?newUser={newUser}", arguments = listOf(
            navArgument("newUser") {
                type = NavType.BoolType
            }
        )) {
            val newUser = it.arguments?.getBoolean("newUser") ?: false
            HomeScreen(appState.coroutineScope, newUser = newUser) {
                appState.navigateAndPopUp(Screens.SIGN_IN.route, Screens.HOME.route)
            }
        }
    }
}