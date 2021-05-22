package com.svenjacobs.app.leon.services.model

sealed class CleaningResult {

    data class Success(
        val originalText: String,
        val cleanedText: String,
        val cleanedParametersCount: Int,
        val urls: List<String>,
    ) : CleaningResult()

    object Failure : CleaningResult()
}
