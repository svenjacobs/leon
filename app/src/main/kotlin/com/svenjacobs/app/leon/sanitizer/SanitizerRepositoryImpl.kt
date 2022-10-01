/*
 * Léon - The URL Cleaner
 * Copyright (C) 2022 Sven Jacobs
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

package com.svenjacobs.app.leon.sanitizer

import com.svenjacobs.app.leon.core.domain.inject.AppComponent
import com.svenjacobs.app.leon.core.domain.sanitizer.SanitizerId
import com.svenjacobs.app.leon.core.domain.sanitizer.SanitizerRegistrations
import com.svenjacobs.app.leon.core.domain.sanitizer.SanitizerRepository
import com.svenjacobs.app.leon.core.domain.sanitizer.SanitizerRepository.SanitizerState
import com.svenjacobs.app.leon.datastore.SanitizerDataStoreManager
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class SanitizerRepositoryImpl(
	private val dataStoreManager: SanitizerDataStoreManager = SanitizerDataStoreManager(),
	private val registrations: SanitizerRegistrations = AppComponent.sanitizerRegistrations,
) : SanitizerRepository {

	override val state: Flow<ImmutableList<SanitizerState>>
		get() = dataStoreManager.data.map { pref ->
			registrations.map { reg ->
				SanitizerState(
					id = reg.id,
					enabled = pref[dataStoreManager.preferencesKey(reg.id.value)] ?: true,
				)
			}.toImmutableList()
		}

	override suspend fun isEnabled(id: SanitizerId): Boolean =
		dataStoreManager.isSanitizerEnabled(id.value).first() ?: true

	override suspend fun setEnabled(id: SanitizerId, enabled: Boolean) {
		dataStoreManager.setSanitizerEnabled(id.value, enabled)
	}
}
