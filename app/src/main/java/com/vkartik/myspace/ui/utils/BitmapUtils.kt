package com.vkartik.myspace.ui.utils

import android.content.res.Resources
import android.graphics.Bitmap
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.palette.graphics.Palette
import kotlin.math.min

fun resizeBitmap(originalBitmap: Bitmap, percentageWidth: Int): Bitmap {
    // Calculate the aspect ratio of the original image
    val aspectRatio = originalBitmap.width.toFloat() / originalBitmap.height.toFloat()

    // Get the screen width and height
    val screenWidth = Resources.getSystem().displayMetrics.widthPixels
    val screenHeight = Resources.getSystem().displayMetrics.heightPixels

    val minSize = min(screenHeight, screenWidth)

    // Calculate the desired width and height for the image
    val desiredWidth = (minSize * percentageWidth) / 100
    val newHeight = (desiredWidth / aspectRatio).toInt()

    // Create a new bitmap and scale the original bitmap
    return Bitmap.createScaledBitmap(originalBitmap, desiredWidth, newHeight, true)
}

fun extractBorderColorFrom(bitmap: Bitmap): Color {
    val palette = Palette.from(bitmap).generate()
    return Color(
        palette.darkVibrantSwatch?.rgb ?: palette.vibrantSwatch?.rgb
        ?: palette.lightVibrantSwatch?.rgb ?: palette.mutedSwatch?.rgb
        ?: palette.darkMutedSwatch?.rgb ?: palette.mutedSwatch?.rgb ?: palette.lightMutedSwatch?.rgb
        ?: Color.Black.toArgb()
    )
}