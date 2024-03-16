package com.vkartik.myspace

import android.content.res.Resources
import androidx.compose.material.ScaffoldState
import androidx.navigation.NavHostController
import kotlinx.coroutines.CoroutineScope

class MySpaceAppState(
    val navController: NavHostController,
    val coroutineScope: CoroutineScope
) {
    fun navigate(route: String) {
        navController.navigate(route) { launchSingleTop = true }
    }

    fun navigateAndPopUp(route: String, popUp: String) {
        navController.navigate(route) {
            launchSingleTop = true
            popUpTo(popUp) { inclusive = true }
        }
    }
}