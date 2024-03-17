package com.vkartik.myspace.data.di

import android.content.Context
import com.vkartik.myspace.data.interactors.repository.CategoryRemoteRepositoryImpl
import com.vkartik.myspace.domain.repository.CategoryRepository
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