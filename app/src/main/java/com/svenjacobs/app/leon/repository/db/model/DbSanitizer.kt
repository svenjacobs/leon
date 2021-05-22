package com.svenjacobs.app.leon.repository.db.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "sanitizers",
    indices = [Index(value = ["name"], unique = true)]
)
data class DbSanitizer(
    @PrimaryKey(autoGenerate = true)
    val uid: Int = 0,
    val type: Type,
    val name: String,
    val data: Map<String, String> = emptyMap(),
    val description: String? = null,
    val isDefault: Boolean = false,
    val isEnabled: Boolean = true,
) {
    enum class Type { QUERY_PARAMETER }
}
