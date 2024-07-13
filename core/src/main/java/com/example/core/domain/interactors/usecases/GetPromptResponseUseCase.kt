package com.example.core.domain.interactors.usecases

import com.example.core.domain.interactors.GenAiRepository
import javax.inject.Inject

class GetPromptResponseUseCase @Inject constructor(private val genAiRepository: GenAiRepository) {
    suspend fun execute(prompt: String): String? = genAiRepository.getTextPromptResponse(prompt)
}