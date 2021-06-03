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

import com.svenjacobs.app.leon.datastore.DataStoreManager
import com.svenjacobs.app.leon.repository.CleanerRepository
import kotlinx.coroutines.flow.first
import timber.log.Timber
import javax.inject.Inject

class Migrations @Inject constructor(
    private val cleanerRepository: CleanerRepository,
    private val dataStoreManager: DataStoreManager,
) {

    suspend fun migrate() {
        val prevVersionCode = dataStoreManager.versionCode.first()

        if (prevVersionCode <= VERSION_0_3_0) {
            Timber.d("Performing migration from version <= 0.3.0")

            // Removing all default sanitizers because they have changed
            cleanerRepository.getSanitizers().first().forEach { sanitizer ->
                if (sanitizer.isDefault) {
                    cleanerRepository.deleteSanitizer(sanitizer)
                }
            }
        }
    }

    private companion object {
        private const val VERSION_0_3_0 = 223
    }
}
