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

package com.svenjacobs.app.leon.repository.db.model

import androidx.room.DatabaseView

/**
 * A view which joins [DbSanitizer] and [DbSanitizerConfig]
 *
 * @see DbSanitizer
 * @see DbSanitizerConfig
 */
@DatabaseView(
    "SELECT sanitizers.uid AS uid, sanitizers.type AS type, sanitizers.name AS name, sanitizers.data AS data, sanitizers.description AS description, sanitizers.isDefault, sanitizer_configs.isEnabled AS isEnabled FROM sanitizers LEFT OUTER JOIN sanitizer_configs ON sanitizers.uid = sanitizer_configs.uid"
)
data class DbSanitizerView(
    val uid: Long,
    val type: DbSanitizer.Type,
    val name: String,
    val data: Map<String, String?>,
    val description: String?,
    val isDefault: Boolean,
    val isEnabled: Boolean?,
)
