package com.vkartik.myspace.data.di

import android.content.Context
import com.example.core.data.interactors.CategoryRepository
import com.vkartik.myspace.data.interactors.repository.CategoryRemoteRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface AppModule {
    @Binds fun bindAppContext(@ApplicationContext context: Context): Context

    @Binds fun bindCategoryRepository(categoryRepositoryImpl: CategoryRemoteRepositoryImpl): CategoryRepository
}