package com.vkartik.myspace.data.di

import android.content.Context
import com.google.android.gms.auth.api.identity.Identity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.vkartik.myspace.data.GoogleAuthUiClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {
    @Provides
    fun firebaseAuth(): FirebaseAuth = Firebase.auth
    @Provides
    fun googleAuthUiClient(@ApplicationContext appContext: Context): GoogleAuthUiClient =
        GoogleAuthUiClient(appContext, Identity.getSignInClient(appContext))
}