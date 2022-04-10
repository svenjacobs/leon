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
import com.svenjacobs.app.leon.repository.db.dao.DbSanitizerConfigDao
import com.svenjacobs.app.leon.repository.db.dao.DbSanitizerDao
import com.svenjacobs.app.leon.repository.db.dao.DbSanitizerViewDao
import com.svenjacobs.app.leon.repository.db.mapper.DbSanitizerMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface CleanerRepository {

    fun getSanitizers(): Flow<List<Sanitizer>>

    suspend fun getSanitizerByName(name: String): Sanitizer?

    suspend fun addSanitizer(
        sanitizer: Sanitizer,
        withConfig: Boolean = true,
    )

    suspend fun updateSanitizer(
        sanitizer: Sanitizer,
        withConfig: Boolean = true,
    )

    suspend fun deleteSanitizer(sanitizer: Sanitizer)

    suspend fun setSanitizerEnabled(
        sanitizer: Sanitizer,
        enabled: Boolean,
    )
}

class CleanerRepositoryImpl @Inject constructor(
    private val sanitizerDao: DbSanitizerDao,
    private val sanitizerConfigDao: DbSanitizerConfigDao,
    private val sanitizerViewDao: DbSanitizerViewDao,
    private val mapper: DbSanitizerMapper,
) : CleanerRepository {

    override fun getSanitizers() =
        sanitizerViewDao.getAll().map { it.map(mapper::mapFromDb) }

    override suspend fun getSanitizerByName(name: String) =
        sanitizerViewDao.getByName(name)?.let {
            mapper.mapFromDb(it)
        }

    override suspend fun addSanitizer(
        sanitizer: Sanitizer,
        withConfig: Boolean,
    ) {
        val (dbSanitizer, dbConfig) = mapper.mapToDb(sanitizer)

        val uid = sanitizerDao.insert(dbSanitizer)

        if (withConfig) {
            sanitizerConfigDao.insert(dbConfig.copy(uid = uid))
        }
    }

    override suspend fun updateSanitizer(
        sanitizer: Sanitizer,
        withConfig: Boolean,
    ) {
        val (dbSanitizer, dbConfig) = mapper.mapToDb(sanitizer)

        sanitizerDao.update(dbSanitizer)

        if (withConfig) {
            sanitizerConfigDao.update(dbConfig)
        }
    }

    override suspend fun deleteSanitizer(sanitizer: Sanitizer) {
        val (dbSanitizer, _) = mapper.mapToDb(sanitizer)

        sanitizerDao.delete(dbSanitizer)
    }

    override suspend fun setSanitizerEnabled(
        sanitizer: Sanitizer,
        enabled: Boolean,
    ) {
        val (_, dbConfig) = mapper.mapToDb(sanitizer)

        sanitizerConfigDao.insert(dbConfig.copy(isEnabled = enabled))
    }
}
