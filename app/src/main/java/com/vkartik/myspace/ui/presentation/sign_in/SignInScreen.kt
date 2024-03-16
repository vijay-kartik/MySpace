package com.vkartik.myspace.ui.presentation.sign_in

import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun SignInScreen(
    viewModel: SignInViewModel = hiltViewModel(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    navigateToHome: () -> Unit
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
            Log.i("SignInScreen", "user already signed in")
            navigateToHome()
            viewModel.resetState()
        }
        state.signInError?.let { error ->
            Log.i("SignInScreen", "sign in error")
            Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
        }
    }
    LaunchedEffect(key1 = internetConnected) {
        if (internetConnected != null && !internetConnected!!) {
            Log.i("SignInScreen", "internet not connected")
            Toast.makeText(context, "Connect to the internet to continue", Toast.LENGTH_SHORT).show()
        }
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp), contentAlignment = Alignment.Center) {
        if (!state.isUserSignedIn) {
            Button(onClick = {
                coroutineScope.launch {
                    Log.i("SignInScreen", "button clicked")

                    if (internetConnected == false) {
                        Log.i("SignInScreen", "internet not connected")
                        Toast.makeText(context, "Connect to the internet to continue", Toast.LENGTH_SHORT).show()
                        return@launch
                    }

                    val signInIntent = viewModel.getSignInIntent()
                    launcher.launch(
                        IntentSenderRequest.Builder(
                            signInIntent ?: return@launch
                        ).build()
                    )
                }
            }) {
                Text("SignIn")
            }
        }
    }

}