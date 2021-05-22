package com.svenjacobs.app.leon.repository.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.svenjacobs.app.leon.repository.db.model.DbSanitizer
import com.svenjacobs.app.leon.repository.db.model.DbSanitizerDao

@Database(entities = [DbSanitizer::class], version = 1)
@TypeConverters(Converters::class)
abstract class CleanerDatabase : RoomDatabase() {

    abstract fun sanitizerDao(): DbSanitizerDao
}
