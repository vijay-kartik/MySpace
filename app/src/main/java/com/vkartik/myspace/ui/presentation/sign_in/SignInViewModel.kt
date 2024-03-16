package com.vkartik.myspace.ui.presentation.sign_in

import android.content.Intent
import android.content.IntentSender
import android.net.ConnectivityManager
import androidx.lifecycle.ViewModel
import com.vkartik.myspace.data.GoogleAuthUiClient
import com.vkartik.myspace.data.interactors.CheckInternetStatusUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val checkInternetStatusUseCase: CheckInternetStatusUseCase,
    private val googleAuthUiClient: GoogleAuthUiClient
) : ViewModel() {
    private val _state = MutableStateFlow(SignInState())
    private val _internetConnected: MutableStateFlow<Boolean?> = MutableStateFlow(null)
    val internetConnected = _internetConnected.asStateFlow()
    val state = _state.asStateFlow()

    init {
        checkInternetStatus()
        if (getCurrentUser() != null) {
            _state.update {
                it.copy(
                    isUserSignedIn = true
                )
            }
        }
    }

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

    private fun getCurrentUser(): UserData? {
        return googleAuthUiClient.getSignedInUser()
    }

    private fun checkInternetStatus() {
        checkInternetStatusUseCase.execute(object: ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: android.net.Network) {
                _internetConnected.value = true
            }

            override fun onLost(network: android.net.Network) {
                _internetConnected.value = false
            }

            override fun onUnavailable() {
                _internetConnected.value = false
            }
        })
    }
}