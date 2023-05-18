/*
 * Léon - The URL Cleaner
 * Copyright (C) 2023 Sven Jacobs
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

package com.svenjacobs.app.leon.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.svenjacobs.app.leon.ui.screens.main.MainScreen
import com.svenjacobs.app.leon.ui.screens.settings.SettingsLicensesScreen
import com.svenjacobs.app.leon.ui.screens.settings.SettingsSanitizersScreen

@Composable
fun MainRouter(sourceText: State<String?>, modifier: Modifier = Modifier) {
	val navController = rememberNavController()

	NavHost(
		modifier = modifier,
		navController = navController,
		startDestination = Routes.Main,
	) {
		composable(Routes.Main) {
			MainScreen(
				sourceText = sourceText,
				onNavigateToSettingsSanitizers = { navController.navigate(Routes.SettingsSanitizers) },
				onNavigateToSettingsLicenses = { navController.navigate(Routes.SettingsLicenses) },
			)
		}

		composable(Routes.SettingsSanitizers) {
			SettingsSanitizersScreen(
				onBackClick = { navController.popBackStack() },
			)
		}

		composable(Routes.SettingsLicenses) {
			SettingsLicensesScreen(
				onBackClick = { navController.popBackStack() },
			)
		}
	}
}

private object Routes {
	const val Main = "main"
	const val SettingsSanitizers = "settings_sanitizers"
	const val SettingsLicenses = "settings_licenses"
}
