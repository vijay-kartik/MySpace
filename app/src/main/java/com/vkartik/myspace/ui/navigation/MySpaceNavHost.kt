package com.vkartik.myspace.ui.navigation

import android.os.Build
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.vkartik.myspace.MySpaceAppState
import com.vkartik.myspace.ui.presentation.Screens
import com.vkartik.myspace.ui.presentation.home.HomeScreen
import com.vkartik.myspace.ui.presentation.sign_in.SignInScreen
import com.vkartik.myspace.ui.presentation.sign_in.SignInViewModel

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