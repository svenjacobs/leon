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

package com.svenjacobs.app.leon.ui.screens.main.model

import android.content.Context
import android.content.Intent
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.svenjacobs.app.leon.R
import com.svenjacobs.app.leon.services.CleanerService
import com.svenjacobs.app.leon.services.model.CleaningResult
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val cleanerService: CleanerService,
) : ViewModel() {

    private val _result = MutableStateFlow<CleaningResult>(CleaningResult.Failure)
    val result = _result.asStateFlow()

    fun setText(text: String?) {
        if (text == null && result.value is CleaningResult.Success) return

        viewModelScope.launch {
            _result.value = cleanerService.clean(text)
        }
    }

    fun buildIntent(text: String): Intent {
        val target = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            addCategory(Intent.CATEGORY_DEFAULT)
            putExtra(Intent.EXTRA_TEXT, text)
        }

        return Intent.createChooser(target, null)
    }

    fun buildCustomTabIntent(): CustomTabsIntent {
        val toolbarColorLight = ContextCompat.getColor(context, R.color.primaryColor)
        val toolbarColorDark = ContextCompat.getColor(context, R.color.nightPrimaryColor)

        val light = CustomTabColorSchemeParams.Builder()
            .setToolbarColor(toolbarColorLight)
            .build()

        val dark = CustomTabColorSchemeParams.Builder()
            .setToolbarColor(toolbarColorDark)
            .build()

        return CustomTabsIntent.Builder()
            .setColorScheme(CustomTabsIntent.COLOR_SCHEME_SYSTEM)
            .setColorSchemeParams(CustomTabsIntent.COLOR_SCHEME_DARK, dark)
            .setDefaultColorSchemeParams(light)
            .build()
    }
}
