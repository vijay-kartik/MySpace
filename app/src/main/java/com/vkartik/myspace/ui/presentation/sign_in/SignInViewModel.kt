package com.vkartik.myspace.ui.presentation.sign_in

import android.content.Intent
import android.content.IntentSender
import androidx.lifecycle.ViewModel
import com.vkartik.myspace.data.GoogleAuthUiClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val googleAuthUiClient: GoogleAuthUiClient
) : ViewModel() {
    private val _state = MutableStateFlow(SignInState())
    val state = _state.asStateFlow()

    private fun onSignInResult(result: SignInResult) {
        _state.update {
            it.copy(
                isSignInSuccess = result.data != null,
                signInError = result.errorMsg
            )
        }
    }

    suspend fun startSignIn(googleSignInUiIntent: Intent?) {
        val signInResult = googleAuthUiClient.signInWithIntent(
            intent = googleSignInUiIntent ?: return
        )
        onSignInResult(signInResult)
    }

    fun resetState() {
        _state.update { SignInState() }
    }

    suspend fun getSignInIntent(): IntentSender? {
        return googleAuthUiClient.signIn()
    }
}