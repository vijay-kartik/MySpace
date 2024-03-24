package com.vkartik.myspace.data.interactors

import android.net.Uri
import android.util.Log
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class UploadFileUseCase @Inject constructor(
    private val firebaseStorage: FirebaseStorage
) {
    suspend fun execute(filePath: String?): String? {
        if (filePath == null) return null

        return suspendCoroutine { continuation ->
            val fileUri = Uri.fromFile(File(filePath))
            val storagePath = "images/" + System.currentTimeMillis() + ".jpg"
            val storageRef = firebaseStorage.reference
            val imagesRef = storageRef.child(storagePath)
            CoroutineScope(Dispatchers.IO).launch {
                val uploadTask = imagesRef.putFile(fileUri)
                uploadTask.addOnSuccessListener {
                    Log.e("UploadUseCase", "upload complete")
                    continuation.resume(storagePath)
                }.addOnCanceledListener {
                    Log.e("UploadUseCase", "upload canceled")
                    continuation.resume(null)
                }.addOnFailureListener {
                    Log.e("UploadUseCase", "upload failed ${it.message} ${it.localizedMessage}")
                    continuation.resume(null)
                }
            }
        }

    }
}