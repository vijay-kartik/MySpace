package com.example.core.data.di

import android.content.Context
import com.example.core.data.MySpaceDb
import com.example.core.data.interactors.CategoryLocalDataSource
import com.example.core.data.interactors.CategoryRepositoryImpl
import com.example.core.data.source.CategoryRemoteDataSource
import com.example.core.domain.interactors.CategoryRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): MySpaceDb {
        return MySpaceDb.getInstance(context)
    }

    @Provides
    fun provideCategoryLocalDataSource(db: MySpaceDb): CategoryLocalDataSource {
        return CategoryLocalDataSource(db.categoryDao())
    }

    @Provides
    fun providesCategoryRemoteDataSource(
        db: FirebaseFirestore,
        firebaseAuth: FirebaseAuth
    ): CategoryRemoteDataSource {
        return CategoryRemoteDataSource(db, firebaseAuth)
    }

    @Provides
    fun provideCategoryRepository(
        localDataSource: CategoryLocalDataSource,
        remoteDataSource: CategoryRemoteDataSource
    ): CategoryRepository {
        return CategoryRepositoryImpl(localDataSource, remoteDataSource)
    }
}