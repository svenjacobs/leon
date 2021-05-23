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

package com.svenjacobs.app.lean.repository

import com.svenjacobs.app.leon.domain.Defaults
import com.svenjacobs.app.leon.domain.model.Sanitizer
import com.svenjacobs.app.leon.repository.CleanerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class CleanerRepositoryFake : CleanerRepository {

    override fun getSanitizers(): Flow<List<Sanitizer>> =
        flowOf(Defaults.SANITIZERS)

    override suspend fun getSanitizerByName(name: String): Sanitizer? =
        Defaults.SANITIZERS.find { it.name == name }

    override suspend fun addSanitizer(sanitizer: Sanitizer) {
        throw NotImplementedError()
    }

    override suspend fun addSanitizers(sanitizers: List<Sanitizer>) {
        throw NotImplementedError()
    }

    override suspend fun updateSanitizer(sanitizer: Sanitizer) {
        throw NotImplementedError()
    }

    override suspend fun deleteSanitizer(sanitizer: Sanitizer) {
        throw NotImplementedError()
    }
}
