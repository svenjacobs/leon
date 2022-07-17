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

package com.svenjacobs.app.leon.core.domain

import com.svenjacobs.app.leon.core.domain.sanitizer.Registrations
import com.svenjacobs.app.leon.core.domain.sanitizer.SanitizerRegistrations
import com.svenjacobs.app.leon.core.domain.sanitizer.SanitizerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URLDecoder
import javax.inject.Inject

class CleanerService @Inject constructor(
    @Registrations private val registrations: SanitizerRegistrations,
    private val repository: SanitizerRepository,
) {

    data class Result(
        val originalText: String,
        val cleanedText: String,
        val urls: List<String>,
    )

    suspend fun clean(
        text: String?,
        decodeUrl: Boolean = false,
    ): Result {
        if (text.isNullOrEmpty()) throw IllegalArgumentException()

        val (cleaned, urls) = URL_REGEX
            .findAll(text)
            .fold(Pair(text, emptyList<String>())) { (currentText, urls), match ->
                val result = cleanUrl(match.value, registrations)
                Pair(currentText.replace(match.value, result), urls + result)
            }
            .let { (cleaned, urls) ->
                val decoded = if (decodeUrl) withContext(Dispatchers.Default) {
                    URLDecoder.decode(
                        cleaned,
                        "UTF-8"
                    )
                } else cleaned

                Pair(decoded, urls)
            }

        return Result(
            originalText = text,
            cleanedText = cleaned,
            urls = urls,
        )
    }

    private suspend fun cleanUrl(
        url: String,
        registrations: SanitizerRegistrations,
    ): String {
        val cleaned = registrations
            .filter { repository.isEnabled(it.id) }
            .filter { it.matchesDomain(url) }
            .fold(url) { currentUrl, registration ->
                registration.sanitizer(currentUrl)
            }

        return if (cleaned.contains('?')) {
            cleaned
        } else {
            cleaned.replaceFirst('&', '?')
        }
    }

    private companion object {
        private val URL_REGEX = Regex("https?://.[^\\s]*")
    }
}