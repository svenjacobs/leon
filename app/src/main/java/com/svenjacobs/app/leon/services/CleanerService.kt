package com.svenjacobs.app.leon.services

import com.svenjacobs.app.leon.domain.model.Sanitizer
import com.svenjacobs.app.leon.repository.CleanerRepository
import com.svenjacobs.app.leon.services.model.CleaningResult
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class CleanerService @Inject constructor(
    private val repository: CleanerRepository,
    private val sanitizerStrategyExecutor: SanitizerStrategyExecutor,
) {

    suspend fun clean(text: String?): CleaningResult {
        if (text.isNullOrEmpty()) return CleaningResult.Failure

        var cleaned: String = text
        var count = 0
        val urls = mutableListOf<String>()
        val sanitizers = repository.getSanitizers().first()

        URL_REGEX.findAll(text).forEach { match ->
            val result = cleanUrl(match.value, sanitizers)
            urls.add(result.cleanedUrl)
            cleaned = cleaned.replace(match.value, result.cleanedUrl)
            count += result.cleanedParametersCount
        }

        return CleaningResult.Success(
            originalText = text,
            cleanedText = cleaned,
            cleanedParametersCount = count,
            urls = urls,
        )
    }

    data class UrlResult(
        val cleanedUrl: String,
        val cleanedParametersCount: Int,
    )

    private fun cleanUrl(
        url: String,
        sanitizers: List<Sanitizer>,
    ): UrlResult {
        var cleaned = url
        var count = 0

        sanitizers.forEach { sanitizer ->
            val result = sanitizerStrategyExecutor.sanitize(sanitizer, cleaned)
            cleaned = result.output
            count += result.artifactsRemoved
        }

        if (!cleaned.contains('?')) {
            cleaned = cleaned.replaceFirst('&', '?')
        }

        return UrlResult(
            cleanedUrl = cleaned,
            cleanedParametersCount = count,
        )
    }

    private companion object {
        private val URL_REGEX = Regex("https?://.[^\\s]*")
    }
}
