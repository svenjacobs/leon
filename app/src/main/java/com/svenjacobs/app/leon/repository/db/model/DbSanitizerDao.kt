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

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface DbSanitizerDao {

    @Query("SELECT * from sanitizers")
    fun getAll(): Flow<List<DbSanitizer>>

    @Query("SELECT * from sanitizers WHERE name = :name")
    suspend fun getByName(name: String): DbSanitizer?

    @Update
    suspend fun update(parameter: DbSanitizer)

    @Insert
    suspend fun insert(parameter: DbSanitizer)

    @Insert
    suspend fun insertAll(parameters: List<DbSanitizer>)

    @Delete
    suspend fun delete(parameter: DbSanitizer)
}
