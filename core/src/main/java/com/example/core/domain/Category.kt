package com.example.core.domain

import android.net.Uri

data class Category(
    val uid: String? = null,
    val name: String,
    val imageUri: Uri? = null,
    val storagePath: String? = null
)