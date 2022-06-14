/*
 * Léon - The URL Cleaner
 * Copyright (C) 2022 Sven Jacobs
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.svenjacobs.app.leon.services

import com.svenjacobs.app.leon.domain.model.Sanitizer
import com.svenjacobs.app.leon.repository.CleanerRepository
import com.svenjacobs.app.leon.services.model.Cleaned
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class CleanerService @Inject constructor(
    private val repository: CleanerRepository,
    private val sanitizerStrategyExecutor: SanitizerStrategyExecutor,
) {

    suspend fun clean(text: String?): Result<Cleaned> {
        if (text.isNullOrEmpty()) return Result.failure(IllegalArgumentException())

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

        return Result.success(
            Cleaned(
                originalText = text,
                cleanedText = cleaned,
                cleanedParametersCount = count,
                urls = urls,
            ),
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
