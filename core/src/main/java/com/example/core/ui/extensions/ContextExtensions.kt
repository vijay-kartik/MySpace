package com.example.core.ui.extensions

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.widget.Toast

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.getBitmapFromUri(uri: Uri): Bitmap {
    return ImageDecoder.decodeBitmap(ImageDecoder.createSource(this.contentResolver, uri))
}

fun Context.getBitmapFromLocalPath(path: String): Bitmap {
    return BitmapFactory.decodeFile(path)
}