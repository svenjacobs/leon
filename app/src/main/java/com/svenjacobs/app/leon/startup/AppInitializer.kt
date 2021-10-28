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

package com.svenjacobs.app.leon.startup

import android.content.Context
import androidx.startup.Initializer
import com.svenjacobs.app.leon.BuildConfig
import com.svenjacobs.app.leon.datastore.DataStoreManager
import com.svenjacobs.app.leon.domain.Defaults
import com.svenjacobs.app.leon.inject.ApplicationCoroutineScope
import com.svenjacobs.app.leon.repository.CleanerRepository
import dagger.hilt.EntryPoint
import dagger.hilt.EntryPoints
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Performs (initial) initialization of app.
 */
@Suppress("unused")
class AppInitializer : Initializer<Unit> {

    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface AppInitializerEntryPoint {

        val cleanerRepository: CleanerRepository

        val dataStoreManager: DataStoreManager

        val migrations: Migrations

        @get:ApplicationCoroutineScope
        val coroutineScope: CoroutineScope

        val stethoHelper: StethoHelper
    }

    override fun create(context: Context) {
        val entryPoint = EntryPoints.get(context, AppInitializerEntryPoint::class.java)

        val migrations = entryPoint.migrations
        val dataStoreManager = entryPoint.dataStoreManager
        val repository = entryPoint.cleanerRepository
        val stethoHelper = entryPoint.stethoHelper
        val coroutineScope = entryPoint.coroutineScope

        stethoHelper.initialize(context)

        coroutineScope.launch {
            migrations.migrate()

            dataStoreManager.setVersionCode(BuildConfig.VERSION_CODE)

            Defaults.Sanitizers.forEach { sanitizer ->
                val exists = repository.getSanitizerByName(sanitizer.name) != null

                if (exists) {
                    repository.updateSanitizer(
                        sanitizer,
                        withConfig = false,
                    )
                } else {
                    repository.addSanitizer(
                        sanitizer,
                        withConfig = false,
                    )
                }
            }
        }
    }

    override fun dependencies() = listOf(TimberInitializer::class.java)
}
