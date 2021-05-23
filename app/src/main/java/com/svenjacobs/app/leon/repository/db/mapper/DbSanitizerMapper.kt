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
import com.svenjacobs.app.leon.repository.db.model.DbSanitizer
import com.svenjacobs.app.leon.repository.db.model.DbSanitizer.Type
import javax.inject.Inject

class DbSanitizerMapper @Inject constructor() {

    fun mapToDb(sanitizer: Sanitizer): DbSanitizer =
        when (sanitizer) {
            is QueryParameterSanitizer ->
                DbSanitizer(
                    uid = sanitizer.uid,
                    type = Type.QUERY_PARAMETER,
                    name = sanitizer.name,
                    data = mapOf(KEY_PARAMETER_NAME to sanitizer.parameterName),
                    description = sanitizer.description,
                    isDefault = sanitizer.isDefault,
                    isEnabled = sanitizer.isEnabled,
                )
        }

    fun mapFromDb(dbSanitizer: DbSanitizer): Sanitizer =
        when (dbSanitizer.type) {
            Type.QUERY_PARAMETER -> QueryParameterSanitizer(
                parameterName = dbSanitizer.data.getValue(KEY_PARAMETER_NAME),
                uid = dbSanitizer.uid,
                name = dbSanitizer.name,
                description = dbSanitizer.description,
                isDefault = dbSanitizer.isDefault,
                isEnabled = dbSanitizer.isEnabled,
            )
        }

    private companion object {
        private const val KEY_PARAMETER_NAME = "parameterName"
    }
}
