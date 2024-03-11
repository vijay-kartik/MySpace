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
            SignInScreen { navController.navigate(Screens.HOME.route) {
                popUpTo(Screens.SIGN_IN.route) {
                    inclusive = true
                }
            } }
        }

        composable(Screens.HOME.route) {
            HomeScreen {
                navController.navigate(Screens.SIGN_IN.route) {
                    popUpTo(Screens.HOME.route) {
                        inclusive = true
                    }
                }
            }
        }
    }
}