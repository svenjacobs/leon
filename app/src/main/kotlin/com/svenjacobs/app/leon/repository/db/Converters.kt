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

package com.svenjacobs.app.leon.repository.db

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import com.svenjacobs.app.leon.repository.db.model.DbSanitizer
import javax.inject.Inject

@ProvidedTypeConverter
@OptIn(ExperimentalStdlibApi::class)
class Converters @Inject constructor(
    private val moshi: Moshi,
) {

    private val adapter by lazy { moshi.adapter<Map<String, String?>>() }

    @TypeConverter
    fun toMap(value: String): Map<String, String?> =
        adapter.fromJson(value) ?: throw IllegalArgumentException()

    @TypeConverter
    fun fromMap(value: Map<String, String?>): String =
        adapter.toJson(value)

    @TypeConverter
    fun toDbSanitizerType(value: String): DbSanitizer.Type =
        DbSanitizer.Type.valueOf(value)

    @TypeConverter
    fun fromDbSanitizerType(value: DbSanitizer.Type): String =
        value.name
}
