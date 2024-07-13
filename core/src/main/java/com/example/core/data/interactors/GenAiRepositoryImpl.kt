package com.example.core.data.interactors

import com.example.core.data.source.PromptDataSource
import com.example.core.domain.interactors.GenAiRepository
import javax.inject.Inject

class GenAiRepositoryImpl @Inject constructor(private val promptDataSource: PromptDataSource): GenAiRepository {
    override suspend fun getTextPromptResponse(prompt: String): String? {
        return promptDataSource.getResponse(prompt)
    }
}