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

package com.svenjacobs.app.leon.domain

import com.svenjacobs.app.leon.domain.model.Sanitizer
import com.svenjacobs.app.leon.domain.model.Sanitizer.RegexSanitizer
import com.svenjacobs.app.leon.domain.model.Sanitizer.RegexSanitizer.Companion.regexForParameter
import com.svenjacobs.app.leon.domain.model.Sanitizer.RegexSanitizer.Companion.regexForWildcardParameter
import com.svenjacobs.app.leon.startup.AppInitializer

object Defaults {

    /**
     * This regex matches all parameters of a URL, so everything starting at "?".
     *
     * Parameters are matched individually instead of just removing everything starting at "?" to
     * be able to get a count of removed parameters from the regex result.
     */
    private const val ALL_PARAMETERS_REGEX = "(?:\\?|&)[^=]+=[^&]*"

    internal val Webtrekk = RegexSanitizer(
        parameterRegex = regexForWildcardParameter("wt_"),
        name = "wt_*",
        description = "Webtrekk (wt_*)",
        isDefault = true,
    )

    internal val GoogleAnalytics = RegexSanitizer(
        parameterRegex = regexForWildcardParameter("ga_|utm_"),
        name = "ga_* & utm_*",
        description = "Google Analytics (ga_*, utm_*)",
        isDefault = true,
    )

    internal val Facebook = RegexSanitizer(
        parameterRegex = regexForWildcardParameter("fb_|fbclid"),
        name = "fb_*",
        description = "Facebook (fb_*, fbclid)",
        isDefault = true,
    )

    internal val Amazon = RegexSanitizer(
        domainRegex = "amazon\\..+\\/dp\\/[0-9A-Z]+",
        parameterRegex = "(?:ref=[^?&]+)|(?:[?&][^=]+=.[^&]*)",
        name = "amazon",
        description = "Amazon",
        isDefault = true,
    )

    internal val Twitter = RegexSanitizer(
        domainRegex = "twitter\\.com",
        parameterRegex = regexForParameter("s|t"),
        name = "twitter",
        description = "Twitter",
        isDefault = true,
    )

    internal val Spotify = RegexSanitizer(
        domainRegex = "spotify\\.com",
        parameterRegex = regexForParameter("si|dl_branch"),
        name = "spotify",
        description = "Spotify",
        isDefault = true,
    )

    internal val Netflix = RegexSanitizer(
        domainRegex = "netflix\\.com",
        parameterRegex = regexForParameter("s|t|trkid|vlang|clip"),
        name = "netflix",
        description = "Netflix",
        isDefault = true,
    )

    internal val Flipkart = RegexSanitizer(
        domainRegex = "flipkart\\.com",
        parameterRegex = ALL_PARAMETERS_REGEX,
        name = "flipkart",
        description = "Flipkart",
        isDefault = true,
    )

    /**
     * List of default [Sanitizer] which are installed during first app start. See [AppInitializer].
     */
    val Sanitizers = listOf(
        Webtrekk,
        GoogleAnalytics,
        Facebook,
        Amazon,
        Twitter,
        Spotify,
        Netflix,
        Flipkart,
    )
}
