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

package com.svenjacobs.app.leon.repository.db.mapper

import com.svenjacobs.app.leon.domain.model.Sanitizer
import com.svenjacobs.app.leon.domain.model.Sanitizer.QueryParameterSanitizer
import com.svenjacobs.app.leon.domain.model.Sanitizer.RegexSanitizer
import com.svenjacobs.app.leon.repository.db.model.DbSanitizer
import com.svenjacobs.app.leon.repository.db.model.DbSanitizer.Type
import com.svenjacobs.app.leon.repository.db.model.DbSanitizerConfig
import com.svenjacobs.app.leon.repository.db.model.DbSanitizerView
import javax.inject.Inject

class DbSanitizerMapper @Inject constructor() {

    fun mapToDb(sanitizer: Sanitizer): Pair<DbSanitizer, DbSanitizerConfig> {
        val dbSanitizer = when (sanitizer) {
            is QueryParameterSanitizer ->
                DbSanitizer(
                    uid = sanitizer.uid,
                    type = Type.QUERY_PARAMETER,
                    name = sanitizer.name,
                    data = mapOf(KEY_PARAMETER_NAME to sanitizer.parameterName),
                    description = sanitizer.description,
                    isDefault = sanitizer.isDefault,
                )
            is RegexSanitizer ->
                DbSanitizer(
                    uid = sanitizer.uid,
                    type = Type.REGEX,
                    name = sanitizer.name,
                    data = mapOf(
                        KEY_DOMAIN_REGEX to sanitizer.domainRegex,
                        KEY_PARAMETER_REGEX to sanitizer.parameterRegex,
                    ),
                    description = sanitizer.description,
                    isDefault = sanitizer.isDefault,
                )
        }

        val dbSanitizerConfig = DbSanitizerConfig(
            uid = sanitizer.uid,
            isEnabled = sanitizer.isEnabled,
        )

        return Pair(dbSanitizer, dbSanitizerConfig)
    }

    fun mapFromDb(dbSanitizerView: DbSanitizerView): Sanitizer =
        when (dbSanitizerView.type) {
            Type.QUERY_PARAMETER -> QueryParameterSanitizer(
                parameterName = dbSanitizerView.data.requireValue(KEY_PARAMETER_NAME),
                uid = dbSanitizerView.uid,
                name = dbSanitizerView.name,
                description = dbSanitizerView.description,
                isDefault = dbSanitizerView.isDefault,
                isEnabled = dbSanitizerView.isEnabled ?: true,
            )
            Type.REGEX -> RegexSanitizer(
                domainRegex = dbSanitizerView.data[KEY_DOMAIN_REGEX],
                parameterRegex = dbSanitizerView.data.requireValue(KEY_PARAMETER_REGEX),
                uid = dbSanitizerView.uid,
                name = dbSanitizerView.name,
                description = dbSanitizerView.description,
                isDefault = dbSanitizerView.isDefault,
                isEnabled = dbSanitizerView.isEnabled ?: true,
            )
        }

    private fun Map<String, String?>.requireValue(key: String): String =
        get(key) ?: throw IllegalArgumentException("Mandatory value for key $key missing")

    private companion object {
        private const val KEY_PARAMETER_NAME = "parameterName"
        private const val KEY_PARAMETER_REGEX = "parameterRegex"
        private const val KEY_DOMAIN_REGEX = "domainRegex"
    }
}
