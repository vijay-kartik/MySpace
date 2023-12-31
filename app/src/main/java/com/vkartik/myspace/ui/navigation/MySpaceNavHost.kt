package com.vkartik.myspace.ui.navigation

import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.vkartik.myspace.ui.presentation.Screens
import com.vkartik.myspace.ui.presentation.home.HomeScreen
import com.vkartik.myspace.ui.presentation.sign_in.SignInScreen
import com.vkartik.myspace.ui.presentation.sign_in.SignInViewModel

@Composable
fun MySpaceNavHost(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = "sign_in") {
        composable(Screens.SIGN_IN.route) {
            SignInScreen { navController.navigate(Screens.HOME.route) }
        }

        composable(Screens.HOME.route) {
            HomeScreen()
        }
    }
}