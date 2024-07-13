package com.example.core.domain.interactors

interface GenAiRepository {
    suspend fun getTextPromptResponse(prompt: String): String?
}