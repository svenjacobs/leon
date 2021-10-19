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

package com.svenjacobs.app.leon.domain

import com.svenjacobs.app.leon.domain.model.Sanitizer
import com.svenjacobs.app.leon.domain.model.Sanitizer.RegexSanitizer
import com.svenjacobs.app.leon.domain.model.Sanitizer.RegexSanitizer.Companion.regexForParameter
import com.svenjacobs.app.leon.startup.AppInitializer

object Defaults {

    /**
     * List of default [Sanitizer] which are installed during first app start. See [AppInitializer].
     */
    val SANITIZERS = listOf(
        RegexSanitizer(
            parameterRegex = regexForParameter("wt_"),
            name = "wt_*",
            description = "Webtrekk (wt_*)",
            isDefault = true,
        ),
        RegexSanitizer(
            parameterRegex = regexForParameter("ga_|utm_"),
            name = "ga_* & utm_*",
            description = "Google Analytics (ga_*, utm_*)",
            isDefault = true,
        ),
        RegexSanitizer(
            parameterRegex = regexForParameter("fb_|fbclid"),
            name = "fb_*",
            description = "Facebook (fb_*, fbclid)",
            isDefault = true,
        ),
        RegexSanitizer(
            domainRegex = "amazon\\..+\\/dp\\/[0-9A-Z]+",
            parameterRegex = "(?:ref=[^?&]+)|(?:[?&][^=]+=.[^&]*)",
            name = "amazon",
            description = "Amazon",
            isDefault = true,
        )
    )
}
