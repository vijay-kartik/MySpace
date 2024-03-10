package com.vkartik.myspace.utils

import android.content.Context
import android.net.Uri
import android.provider.MediaStore


fun getPath(context: Context, uri: Uri?): String? {
    if (uri == null) return null
    val projection = arrayOf(MediaStore.Images.Media.DATA)
    val cursor = context.contentResolver.query(uri, projection, null, null, null)
    return try {
        val columnIndex = cursor?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor?.moveToFirst()
        cursor?.getString(columnIndex?:return null)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    } finally {
        cursor?.close()
    }

}