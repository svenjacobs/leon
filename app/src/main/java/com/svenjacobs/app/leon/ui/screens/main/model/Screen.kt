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

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.svenjacobs.app.leon.R

sealed class Screen(
    val route: String,
    val icon: ImageVector,
    @StringRes val resourceId: Int,
) {
    object Home : Screen("home", Icons.Filled.Home, R.string.screen_home)
    object Settings : Screen("settings", Icons.Filled.Settings, R.string.screen_settings)
    object SettingsParameters :
        Screen("settings_parameters", Icons.Filled.Settings, R.string.screen_settings)
}
