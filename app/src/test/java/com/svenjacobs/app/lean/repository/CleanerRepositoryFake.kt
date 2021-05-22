package com.svenjacobs.app.lean.repository

import com.svenjacobs.app.leon.domain.Defaults
import com.svenjacobs.app.leon.domain.model.Sanitizer
import com.svenjacobs.app.leon.repository.CleanerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class CleanerRepositoryFake : CleanerRepository {

    override fun getSanitizers(): Flow<List<Sanitizer>> =
        flowOf(Defaults.SANITIZERS)

    override suspend fun getSanitizerByName(name: String): Sanitizer? =
        Defaults.SANITIZERS.find { it.name == name }

    override suspend fun addSanitizer(sanitizer: Sanitizer) {
        throw NotImplementedError()
    }

    override suspend fun addSanitizers(sanitizers: List<Sanitizer>) {
        throw NotImplementedError()
    }

    override suspend fun updateSanitizer(sanitizer: Sanitizer) {
        throw NotImplementedError()
    }

    override suspend fun deleteSanitizer(sanitizer: Sanitizer) {
        throw NotImplementedError()
    }
}
