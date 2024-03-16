package com.vkartik.myspace.domain

import android.net.Uri

data class Category(
    val name: String,
    val imageUri: Uri? = null
)