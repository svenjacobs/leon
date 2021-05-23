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

sealed class Sanitizer(
    open val uid: Int,
    open val name: String,
    open val description: String?,
    open val isDefault: Boolean,
    open val isEnabled: Boolean,
) {

    abstract fun withEnabled(isEnabled: Boolean): Sanitizer

    // TODO: Implement a regex-based query parameter sanitizer
    data class QueryParameterSanitizer(
        val parameterName: String,
        override val uid: Int = 0,
        override val name: String,
        override val description: String? = null,
        override val isDefault: Boolean = false,
        override val isEnabled: Boolean = true,
    ) : Sanitizer(uid, name, description, isDefault, isEnabled) {

        override fun withEnabled(isEnabled: Boolean) =
            copy(isEnabled = isEnabled)
    }
}
