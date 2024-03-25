package com.example.core.data.source

import android.util.Log
import com.example.core.domain.Category
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class CategoryRemoteDataSource @Inject constructor(
    private val db: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth
) {
    suspend fun createCategory(category: Category): Boolean {
        return suspendCoroutine { continuation ->
            val categoryDoc = hashMapOf(
                "uid" to category.uid,
                "name" to category.name,
                "storagePath" to category.storagePath
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

    suspend fun getAllCategories(): List<Category> {
        return suspendCoroutine { continuation ->
            db.collection("users").document("${firebaseAuth.uid}").collection("categories").get().addOnSuccessListener {
                val categories = it.map { queryDocumentSnapshot -> Category(uid = queryDocumentSnapshot["uid"] as String, name = queryDocumentSnapshot["name"] as String, storagePath = queryDocumentSnapshot["storagePath"] as String?) }
                continuation.resume(categories)
            }.addOnFailureListener {
                Log.i("kartikd", "exception : ${it.message}")
                continuation.resume(emptyList())
            }
        }
    }
}