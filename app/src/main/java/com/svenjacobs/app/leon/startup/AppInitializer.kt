package com.svenjacobs.app.leon.startup

import android.content.Context
import androidx.startup.Initializer
import com.svenjacobs.app.leon.domain.Defaults
import com.svenjacobs.app.leon.repository.CleanerRepository
import dagger.hilt.EntryPoint
import dagger.hilt.EntryPoints
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.runBlocking
import timber.log.Timber

@Suppress("unused")
class AppInitializer : Initializer<Unit> {

    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface AppInitializerEntryPoint {

        val cleanerRepository: CleanerRepository
    }

    override fun create(context: Context) {
        val entryPoint = EntryPoints.get(context, AppInitializerEntryPoint::class.java)
        val repository = entryPoint.cleanerRepository

        runBlocking {
            Defaults.SANITIZERS.forEach { sanitizer ->
                val exists = repository.getSanitizerByName(sanitizer.name) != null

                if (!exists) {
                    Timber.d("Inserting default \"${sanitizer.name}\"")
                    repository.addSanitizer(sanitizer)
                } else {
                    Timber.d("Not inserting \"${sanitizer.name}\" because it already exists")
                }
            }
        }
    }

    override fun dependencies() = listOf(TimberInitializer::class.java)
}