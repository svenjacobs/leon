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
