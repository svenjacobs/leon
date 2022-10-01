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

package com.svenjacobs.app.leon.core.domain.inject

import android.content.Context
import com.svenjacobs.app.leon.core.domain.sanitizer.SanitizerRegistration
import com.svenjacobs.app.leon.core.domain.sanitizer.SanitizerRepository
import kotlinx.collections.immutable.ImmutableCollection
import kotlinx.coroutines.MainScope

object AppComponent {

	fun setup(
		appContext: Context,
		sanitizerRepositoryProvider: () -> SanitizerRepository,
		sanitizerRegistrations: ImmutableCollection<SanitizerRegistration>,
	) {
		this.appContext = appContext
		this.sanitizerRepositoryProvider = sanitizerRepositoryProvider
		this.sanitizerRegistrations = sanitizerRegistrations
	}

	private lateinit var sanitizerRepositoryProvider: () -> SanitizerRepository

	lateinit var appContext: Context
		private set

	val appCoroutineScope by lazy { MainScope() }

	lateinit var sanitizerRegistrations: ImmutableCollection<SanitizerRegistration>
		private set

	val sanitizerRepository: SanitizerRepository by lazy { sanitizerRepositoryProvider() }
}
