package com.example.core.data.source

import com.google.firebase.vertexai.FirebaseVertexAI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class PromptDataSource @Inject constructor(private val firebaseVertexAI: FirebaseVertexAI) {
    private val generativeModel = firebaseVertexAI.generativeModel("gemini-1.5-flash")

    suspend fun getResponse(prompt: String): String? {
        return suspendCoroutine { continuation ->
            CoroutineScope(Dispatchers.IO).launch {
                val response = generativeModel.generateContent(prompt)
                continuation.resume(response.text)
            }
        }
    }
}