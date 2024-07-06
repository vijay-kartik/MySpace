package com.vkartik.myspace.ui.presentation.sign_in

import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.core.ui.extensions.showToast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun SignInScreen(
    viewModel: SignInViewModel = hiltViewModel(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    navigateToHome: (Boolean) -> Unit
) {
    val context = LocalContext.current
    val state: SignInState by viewModel.state.collectAsStateWithLifecycle()
    val internetConnected: Boolean? by viewModel.internetConnected.collectAsStateWithLifecycle()

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult(),
        onResult = { result ->
            if (result.resultCode == ComponentActivity.RESULT_OK) {
                coroutineScope.launch {
                    viewModel.startSignIn(result.data)
                }
            }
        }
    )

    LaunchedEffect(key1 = state) {
        if (state.isUserSignedIn || state.isSignInSuccess) {
            navigateToHome(state.isSignInSuccess)
            viewModel.resetState()
        }
        state.signInError?.let { error ->
            context.showToast(error)
        }
    }
    LaunchedEffect(key1 = internetConnected) {
        if (internetConnected != null && !internetConnected!!) {
            context.showToast("Connect to the internet to continue")
        }
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp), contentAlignment = Alignment.Center) {
        if (!state.isUserSignedIn) {
            Column {
                Text("Welcome to your space", style = MaterialTheme.typography.bodyLarge.copy(color = Color.White))

                Button(onClick = {
                    coroutineScope.launch {
                        if (internetConnected == false) {
                            context.showToast("Connect to the internet to continue")
                            return@launch
                        }

                        val signInIntent = viewModel.getSignInIntent()
                        signInIntent?.let {
                            launcher.launch(
                                IntentSenderRequest.Builder(
                                    signInIntent
                                ).build()
                            )
                        } ?: context.showToast("Can't sign in due to internal error.")

                    }
                }) {
                    Text("SignIn")
                }
            }
        }
    }
}