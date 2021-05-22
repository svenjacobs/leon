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

    private val adapter by lazy { moshi.adapter<Map<String, String>>() }

    @TypeConverter
    fun toMap(value: String): Map<String, String> =
        adapter.fromJson(value) ?: throw IllegalArgumentException()

    @TypeConverter
    fun fromMap(value: Map<String, String>): String =
        adapter.toJson(value)

    @TypeConverter
    fun toDbSanitizerType(value: String): DbSanitizer.Type =
        DbSanitizer.Type.valueOf(value)

    @TypeConverter
    fun fromDbSanitizerType(value: DbSanitizer.Type): String =
        value.name
}
