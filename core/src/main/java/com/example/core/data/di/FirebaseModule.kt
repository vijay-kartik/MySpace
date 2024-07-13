package com.example.core.data.di

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.vertexai.vertexAI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {
    @Provides
    fun firebaseStorage() = FirebaseStorage.getInstance()

    @Provides
    fun firebaseFirestore() = Firebase.firestore

    @Provides
    fun firebaseVertexAi() = com.google.firebase.Firebase.vertexAI

}