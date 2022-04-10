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

import androidx.room.*
import androidx.room.migration.AutoMigrationSpec
import com.svenjacobs.app.leon.repository.db.dao.DbSanitizerConfigDao
import com.svenjacobs.app.leon.repository.db.dao.DbSanitizerDao
import com.svenjacobs.app.leon.repository.db.dao.DbSanitizerViewDao
import com.svenjacobs.app.leon.repository.db.model.DbSanitizer
import com.svenjacobs.app.leon.repository.db.model.DbSanitizerConfig
import com.svenjacobs.app.leon.repository.db.model.DbSanitizerView

@Database(
    entities = [
        DbSanitizer::class,
        DbSanitizerConfig::class,
    ],
    views = [DbSanitizerView::class],
    version = 2,
    autoMigrations = [
        AutoMigration(
            from = 1,
            to = 2,
            spec = CleanerDatabase.AutoMigration::class
        ),
    ]
)
@TypeConverters(Converters::class)
abstract class CleanerDatabase : RoomDatabase() {

    @DeleteColumn(
        tableName = "sanitizers",
        columnName = "isEnabled",
    )
    class AutoMigration : AutoMigrationSpec

    abstract fun sanitizerDao(): DbSanitizerDao

    abstract fun sanitizerConfigDao(): DbSanitizerConfigDao

    abstract fun sanitizerViewDao(): DbSanitizerViewDao
}
