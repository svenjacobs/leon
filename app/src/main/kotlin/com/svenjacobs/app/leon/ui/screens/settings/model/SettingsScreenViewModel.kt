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
import android.content.ComponentName
import android.content.Context
import android.content.pm.PackageManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn

@HiltViewModel
@SuppressLint("StaticFieldLeak")
class SettingsScreenViewModel @Inject constructor(
	@ApplicationContext private val context: Context,
) : ViewModel() {

	data class UiState(
		val browserEnabled: Boolean? = null,
	)

	private val browserEnabled = MutableStateFlow<Boolean?>(null)

	val uiState: StateFlow<UiState> =
		browserEnabled
			.mapLatest {
				UiState(
					browserEnabled = it,
				)
			}
			.stateIn(
				scope = viewModelScope,
				started = SharingStarted.WhileSubscribed(5_000),
				initialValue = UiState(),
			)

	init {
		val enabledSetting = packageManager.getComponentEnabledSetting(componentName)
		browserEnabled.value = enabledSetting == PackageManager.COMPONENT_ENABLED_STATE_ENABLED
	}

	fun onBrowserSwitchCheckedChange(checked: Boolean) {
		browserEnabled.value = checked
		packageManager.setComponentEnabledSetting(
			componentName,
			if (checked) {
				PackageManager.COMPONENT_ENABLED_STATE_ENABLED
			} else {
				PackageManager.COMPONENT_ENABLED_STATE_DISABLED
			},
			PackageManager.DONT_KILL_APP,
		)
	}

	private val packageManager: PackageManager
		get() = context.packageManager

	private val componentName: ComponentName
		get() = ComponentName(context.packageName, "${context.packageName}.$COMPONENT_NAME_CLASS")

	private companion object {
		const val COMPONENT_NAME_CLASS = "MainBrowserActivity"
	}
}
