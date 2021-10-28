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

package com.svenjacobs.app.leon.domain.model

import com.svenjacobs.app.leon.domain.model.Sanitizer.RegexSanitizer.Companion.regexForWildcardParameter
import com.svenjacobs.app.leon.services.QueryParameterSanitizerStrategy
import com.svenjacobs.app.leon.services.RegexSanitizerStrategy
import com.svenjacobs.app.leon.services.SanitizerStrategy

/**
 * @see SanitizerStrategy
 */
sealed class Sanitizer(
    open val uid: Long,
    open val name: String,
    open val description: String?,
    open val isDefault: Boolean,
    open val isEnabled: Boolean,
) {

    /**
     * Simple [Sanitizer] which just removes a single parameter declared via [parameterName]
     *
     * @param parameterName Name of parameter to be removed
     *
     * @see QueryParameterSanitizerStrategy
     */
    data class QueryParameterSanitizer(
        val parameterName: String,
        override val uid: Long = 0,
        override val name: String,
        override val description: String? = null,
        override val isDefault: Boolean = false,
        override val isEnabled: Boolean = true,
    ) : Sanitizer(uid, name, description, isDefault, isEnabled)

    /**
     * Regex-based [Sanitizer] which removes parts from URLs based on a regex declared via [parameterRegex]
     *
     * Additionally [domainRegex] can be used to apply this sanitizer only to domains matching
     * this regex, if specified.
     *
     * @param domainRegex Optional regex which should match desired domain(s)
     * @param parameterRegex Regex of parameter, see [regexForWildcardParameter]
     *
     * @see RegexSanitizerStrategy
     */
    data class RegexSanitizer(
        val domainRegex: String? = null,
        val parameterRegex: String,
        override val uid: Long = 0,
        override val name: String,
        override val description: String? = null,
        override val isDefault: Boolean = false,
        override val isEnabled: Boolean = true,
    ) : Sanitizer(uid, name, description, isDefault, isEnabled) {

        companion object {

            /**
             * Returns a regex string which matches a certain parameter
             *
             * For example `regexForParameter("abc")` returns a regex string which matches
             * `?abc=` or `&abc=`.
             *
             * @param parameter Parameter prefix
             */
            fun regexForParameter(parameter: String): String =
                "[?&](?:$parameter)=.[^&]*"

            /**
             * Returns a regex string which matches a certain parameter prefix
             *
             * For example `regexForWildcardParameter("abc_")` returns a regex string which matches
             * `?abc_x=`, `&abc_y=`, `&abc_zzz=` et cetera.
             *
             * @param parameter Parameter prefix
             */
            fun regexForWildcardParameter(parameter: String): String =
                "[?&](?:$parameter)[^=]*=.[^&]*"
        }
    }
}
