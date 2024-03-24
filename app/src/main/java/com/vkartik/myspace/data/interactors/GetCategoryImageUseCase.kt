package com.vkartik.myspace.data.interactors

import android.content.Context
import com.google.firebase.storage.FirebaseStorage
import java.io.File
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class GetCategoryImageUseCase @Inject constructor(
    private val firebaseStorage: FirebaseStorage,
    private val context: Context
) {
    suspend fun execute(storagePath: String): String? {
        return suspendCoroutine { continuation ->
            val localFilePath = storagePath.replace("/", "_")
            val localFile = File(context.filesDir, localFilePath)
            if (localFile.exists()) {
                continuation.resume(localFile.absolutePath)
            } else {
                val imageRef = firebaseStorage.getReference(storagePath)
                imageRef.getFile(localFile).addOnSuccessListener {
                    continuation.resume(localFile.absolutePath)
                }.addOnFailureListener {
                    continuation.resume(null)
                }
            }
        }
    }
}