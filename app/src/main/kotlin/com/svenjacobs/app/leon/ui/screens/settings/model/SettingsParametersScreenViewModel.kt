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

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.svenjacobs.app.leon.domain.model.Sanitizer
import com.svenjacobs.app.leon.repository.CleanerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsParametersScreenViewModel @Inject constructor(
    private val cleanerRepository: CleanerRepository,
) : ViewModel() {

    private val _sanitizers = MutableStateFlow<List<Sanitizer>>(emptyList())

    /**
     * Unfortunately it seems that Room observable queries don't work with database views.
     * So we have to fake it here by manually updating the [StateFlow].
     */
    private fun updateSanitizers() {
        viewModelScope.launch {
            _sanitizers.value = cleanerRepository.getSanitizers().first()
        }
    }

    val sanitizers: StateFlow<List<Sanitizer>>
        get() {
            updateSanitizers()
            return _sanitizers.asStateFlow()
        }

    fun setEnabled(
        sanitizer: Sanitizer,
        enabled: Boolean,
    ) {
        viewModelScope.launch {
            cleanerRepository.setSanitizerEnabled(sanitizer, enabled)
            updateSanitizers()
        }
    }
}
