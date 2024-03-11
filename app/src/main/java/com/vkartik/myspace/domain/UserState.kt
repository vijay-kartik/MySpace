package com.vkartik.myspace.domain

import android.graphics.Bitmap

data class UserState(
    val isUserSignedIn: Boolean = false,
    val userName: String? = null,
    val profilePic: Bitmap? = null
)