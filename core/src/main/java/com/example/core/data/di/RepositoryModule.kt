package com.example.core.data.di

import com.example.core.data.interactors.GenAiRepositoryImpl
import com.example.core.data.source.PromptDataSource
import com.example.core.domain.interactors.GenAiRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    fun providesGenAiRepository(promptDataSource: PromptDataSource): GenAiRepository =
        GenAiRepositoryImpl(promptDataSource)
}