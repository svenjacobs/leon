/*
 * Léon - The URL Cleaner
 * Copyright (C) 2021 Sven Jacobs
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
import com.svenjacobs.app.leon.domain.model.Sanitizer.QueryParameterSanitizer
import com.svenjacobs.app.leon.domain.model.Sanitizer.RegexSanitizer
import com.svenjacobs.app.leon.services.SanitizerStrategy.Result
import javax.inject.Inject

interface SanitizerStrategy<T : Sanitizer> {

    data class Result(
        val output: String,
        val artifactsRemoved: Int,
    )

    fun sanitize(
        sanitizer: T,
        input: String
    ): Result
}

class QueryParameterSanitizerStrategy @Inject constructor() :
    SanitizerStrategy<QueryParameterSanitizer> {

    override fun sanitize(
        sanitizer: QueryParameterSanitizer,
        input: String,
    ): Result {
        val regex = Regex("[?&]${sanitizer.name}=.[^&]*")
        return when (regex.containsMatchIn(input)) {
            true -> Result(
                output = regex.replace(input, ""),
                artifactsRemoved = 1
            )
            false -> Result(
                output = input,
                artifactsRemoved = 0,
            )
        }
    }
}

class RegexSanitizerStrategy @Inject constructor() :
    SanitizerStrategy<RegexSanitizer> {

    override fun sanitize(
        sanitizer: RegexSanitizer,
        input: String,
    ): Result {
        if (sanitizer.domainRegex != null) {
            val domainRegex = Regex(sanitizer.domainRegex)

            if (!domainRegex.containsMatchIn(input)) {
                return Result(
                    output = input,
                    artifactsRemoved = 0,
                )
            }
        }

        val parameterRegex = Regex(sanitizer.parameterRegex)
        val count = parameterRegex.findAll(input).count()

        return Result(
            output = parameterRegex.replace(input, ""),
            artifactsRemoved = count,
        )
    }
}
