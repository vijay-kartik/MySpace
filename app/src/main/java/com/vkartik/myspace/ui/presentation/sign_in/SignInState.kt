package com.vkartik.myspace.ui.presentation.sign_in

data class SignInState(
    val isUserSignedIn: Boolean = false,
    val isSignInSuccess: Boolean = false,
    val signInError: String? = null
)