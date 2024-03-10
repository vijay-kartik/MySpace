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
    
    LaunchedEffect(key1 = state.signInError) {
        state.signInError?.let { error ->
            Log.i("SignInScreen", "sign in error")
            Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(key1 = state.isSignInSuccess) {
        if (state.isSignInSuccess) {
            Log.i("SignInScreen", "launch effect signin success")
            navigateToHome()
            viewModel.resetState()
        }
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp), contentAlignment = Alignment.Center) {
        Button(onClick = {
            coroutineScope.launch {
                Log.i("SignInScreen", "button clicked")

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