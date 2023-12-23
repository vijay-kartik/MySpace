package com.vkartik.myspace.ui.presentation.sign_in

data class SignInState(
    val isSignInSuccess: Boolean = false,
    val signInError: String? = null
)