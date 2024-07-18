package com.vkartik.myspace

import androidx.navigation.NavHostController
import com.vkartik.myspace.ui.navigation.Routes
import kotlinx.coroutines.CoroutineScope

class MySpaceAppState(
    val navController: NavHostController,
    val coroutineScope: CoroutineScope
) {

    fun navigate(route: Routes) {
        navController.navigate(route) { launchSingleTop = true }
    }

    fun navigateAndPopUp(route: Routes, popUp: Routes) {
        navController.navigate(route) {
            launchSingleTop = true
            popUpTo(popUp) { inclusive = true }
        }
    }
}