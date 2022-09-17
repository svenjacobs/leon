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

package com.svenjacobs.app.leon.ui.screens.settings.model

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.svenjacobs.app.leon.core.domain.sanitizer.Registrations
import com.svenjacobs.app.leon.core.domain.sanitizer.SanitizerId
import com.svenjacobs.app.leon.core.domain.sanitizer.SanitizerRegistrations
import com.svenjacobs.app.leon.core.domain.sanitizer.SanitizerRepository
import com.svenjacobs.app.leon.ui.screens.settings.model.SettingsSanitizersScreenViewModel.UiState.Sanitizer
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
@SuppressLint("StaticFieldLeak")
class SettingsSanitizersScreenViewModel @Inject constructor(
	@ApplicationContext private val context: Context,
	@Registrations private val registrations: SanitizerRegistrations,
	private val repository: SanitizerRepository,
) : ViewModel() {

	data class UiState(
		val sanitizers: ImmutableList<Sanitizer> = persistentListOf(),
	) {
		data class Sanitizer(
			val id: SanitizerId,
			val name: String,
			val enabled: Boolean,
		)
	}

	val uiState: StateFlow<UiState> =
		repository.state.map { states ->
			UiState(
				sanitizers = states
					.map { state ->
						Sanitizer(
							id = state.id,
							name = registrations.first { it.id == state.id }.getName(context),
							enabled = state.enabled,
						)
					}
					.sortedBy { it.name }
					.toImmutableList(),
			)
		}.stateIn(
			scope = viewModelScope,
			started = SharingStarted.WhileSubscribed(5_000),
			initialValue = UiState(),
		)

	fun onSanitizerCheckedChange(id: SanitizerId, enabled: Boolean) {
		viewModelScope.launch {
			repository.setEnabled(id, enabled)
		}
	}
}
