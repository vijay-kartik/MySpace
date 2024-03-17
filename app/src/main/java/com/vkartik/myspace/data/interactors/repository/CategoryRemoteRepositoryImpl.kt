package com.vkartik.myspace.data.interactors.repository

import android.util.Log
import androidx.core.net.toUri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.vkartik.myspace.domain.Category
import com.vkartik.myspace.domain.repository.CategoryRepository
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class CategoryRemoteRepositoryImpl @Inject constructor(
    private val db: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth
) : CategoryRepository {
    override suspend fun createCategory(category: Category): Boolean {
        return suspendCoroutine { continuation ->
            val categoryId = "cat" + "_" + System.currentTimeMillis()
            val categoryDoc = hashMapOf(
                "uid" to categoryId,
                "name" to category.name,
                "imageUri" to category.imageUri
            )
            db.collection("users").document("${firebaseAuth.uid}").collection("categories")
                .add(categoryDoc).addOnSuccessListener {
                    continuation.resume(true)
                }.addOnFailureListener {
                    Log.i("kartikd", "exception : ${it.message}")
                    continuation.resume(false)
                }
        }
    }

    override suspend fun getAllCategories(): List<Category> {
        return suspendCoroutine { continuation ->
            db.collection("users").document("${firebaseAuth.uid}").collection("categories").get().addOnSuccessListener {
                val categories = it.map { queryDocumentSnapshot -> Category(name = queryDocumentSnapshot["name"] as String, imageUri = (queryDocumentSnapshot["imageUri"] as String?)?.toUri()) }
                continuation.resume(categories)
            }.addOnFailureListener {
                Log.i("kartikd", "exception : ${it.message}")
                continuation.resume(emptyList())
            }
        }
    }

}