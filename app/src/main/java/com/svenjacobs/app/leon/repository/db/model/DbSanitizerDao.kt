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
