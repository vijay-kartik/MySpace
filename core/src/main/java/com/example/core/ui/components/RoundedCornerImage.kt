package com.example.core.ui.components

import android.graphics.Bitmap
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.core.ui.extensions.extractBorderColorFrom
import com.example.core.ui.extensions.getBitmapFromUri
import com.example.core.ui.extensions.resizeBitmap

@Composable
fun RoundedCornerImage(
    uri: Uri,
    resizeWidth: Int,
    resizeHeight: Int,
    cornerRadius: Dp,
    borderWidth: Dp
) {
    val bitmap = LocalContext.current.getBitmapFromUri(uri)
    val resizedBitmap = resizeBitmap(bitmap, resizeWidth.dp, LocalDensity.current)
    val toImageShape = RoundedCornerShape(cornerRadius)
    val borderStroke = BorderStroke(
        borderWidth, color = extractBorderColorFrom(
            resizedBitmap.copy(
                Bitmap.Config.ARGB_8888, true
            )
        )
    )
    Image(
        resizedBitmap.asImageBitmap(),
        null,
        Modifier
            .clip(toImageShape)
            .border(borderStroke, toImageShape)
    )
}