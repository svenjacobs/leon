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

package com.svenjacobs.app.leon.ui.screens.main.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.svenjacobs.app.leon.core.common.result.UiResult
import com.svenjacobs.app.leon.core.domain.CleanerService
import com.svenjacobs.app.leon.core.domain.CleanerService.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val cleanerService: CleanerService,
) : ViewModel() {

    data class UiState(
        val result: UiResult<Result> = UiResult.Loading,
    )

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    fun setText(
        text: String?,
    ) {
        if (text == null && uiState.value.result is UiResult.Success) return

        viewModelScope.launch {
            _uiState.update { uiState ->
                try {
                    val result = cleanerService.clean(text)

                    uiState.copy(
                        result = UiResult.Success(result)
                    )
                } catch (e: Exception) {
                    uiState.copy(
                        result = UiResult.Error(e)
                    )
                }
            }
        }
    }
}
