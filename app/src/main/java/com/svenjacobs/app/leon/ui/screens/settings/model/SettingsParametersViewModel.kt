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

package com.svenjacobs.app.leon.ui.screens.settings.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.svenjacobs.app.leon.domain.model.Sanitizer
import com.svenjacobs.app.leon.repository.CleanerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsParametersViewModel @Inject constructor(
    private val cleanerRepository: CleanerRepository,
) : ViewModel() {

    val sanitizers
        get() = cleanerRepository.getSanitizers()

    fun setEnabled(
        sanitizer: Sanitizer,
        enabled: Boolean,
    ) {
        viewModelScope.launch {
            cleanerRepository.updateSanitizer(
                sanitizer.withEnabled(enabled)
            )
        }
    }
}
