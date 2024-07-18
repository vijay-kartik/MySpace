package com.vkartik.myspace

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.vkartik.myspace.ui.navigation.MySpaceNavHost
import com.vkartik.myspace.ui.navigation.Routes
import com.vkartik.myspace.ui.theme.MySpaceTheme
import kotlinx.coroutines.CoroutineScope

@Composable
fun MySpaceApp() {
    MySpaceTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.surface
        ) {
            MySpaceNavHost(
                appState = rememberMySpaceAppState(),
                startDestination = Routes.SignIn
            )
        }
    }
}

@Composable
fun rememberMySpaceAppState(
    navController: NavHostController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope()
): MySpaceAppState =
    remember(navController, coroutineScope) {
        MySpaceAppState(navController, coroutineScope)
    }

