package com.vkartik.myspace.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.vkartik.myspace.MySpaceAppState
import com.vkartik.myspace.ui.presentation.Screens
import com.vkartik.myspace.ui.presentation.home.HomeScreen
import com.vkartik.myspace.ui.presentation.sign_in.SignInScreen

@Composable
fun MySpaceNavHost(appState: MySpaceAppState, startDestination: String) {
    NavHost(navController = appState.navController, startDestination = startDestination) {
        composable(Screens.SIGN_IN.route) {
            SignInScreen {
                appState.navigateAndPopUp(Screens.HOME.route, Screens.SIGN_IN.route)
            }
        }

        composable(Screens.HOME.route) {
            HomeScreen(appState.coroutineScope) {
                appState.navigateAndPopUp(Screens.SIGN_IN.route, Screens.HOME.route)
            }
        }
    }
}