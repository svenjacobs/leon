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

package com.svenjacobs.app.leon.repository

import com.svenjacobs.app.leon.domain.model.Sanitizer
import com.svenjacobs.app.leon.repository.db.mapper.DbSanitizerMapper
import com.svenjacobs.app.leon.repository.db.model.DbSanitizerDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface CleanerRepository {

    fun getSanitizers(): Flow<List<Sanitizer>>

    suspend fun getSanitizerByName(name: String): Sanitizer?

    suspend fun addSanitizer(sanitizer: Sanitizer)

    suspend fun addSanitizers(sanitizers: List<Sanitizer>)

    suspend fun updateSanitizer(sanitizer: Sanitizer)

    suspend fun deleteSanitizer(sanitizer: Sanitizer)
}

class CleanerRepositoryImpl @Inject constructor(
    private val dao: DbSanitizerDao,
    private val mapper: DbSanitizerMapper,
) : CleanerRepository {

    override fun getSanitizers() =
        dao.getAll().map { it.map(mapper::mapFromDb) }

    override suspend fun getSanitizerByName(name: String) =
        dao.getByName(name)?.let {
            mapper.mapFromDb(it)
        }

    override suspend fun addSanitizer(sanitizer: Sanitizer) {
        dao.insert(mapper.mapToDb(sanitizer))
    }

    override suspend fun addSanitizers(sanitizers: List<Sanitizer>) {
        dao.insertAll(sanitizers.map(mapper::mapToDb))
    }

    override suspend fun updateSanitizer(sanitizer: Sanitizer) {
        dao.update(mapper.mapToDb(sanitizer))
    }

    override suspend fun deleteSanitizer(sanitizer: Sanitizer) {
        dao.delete(mapper.mapToDb(sanitizer))
    }
}
