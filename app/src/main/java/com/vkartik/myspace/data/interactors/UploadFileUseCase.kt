package com.vkartik.myspace.data.interactors

import android.net.Uri
import android.util.Log
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

class UploadFileUseCase @Inject constructor(private val firebaseStorage: FirebaseStorage) {
    fun execute(filePath: String?) {
        if (filePath == null) return
        val fileUri = Uri.fromFile(File(filePath))
        val storageRef = firebaseStorage.reference
        val imagesRef = storageRef.child("images/sample_image.jpg")
        CoroutineScope(Dispatchers.IO).launch {
            val uploadTask = imagesRef.putFile(fileUri)
            uploadTask.addOnSuccessListener {
                Log.e("UploadUseCase", "upload complete")
            }.addOnCanceledListener {
                Log.e("UploadUseCase", "upload canceled")
            }.addOnFailureListener {
                Log.e("UploadUseCase", "upload failed")
            }
        }
    }
}