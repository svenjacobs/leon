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

package com.svenjacobs.app.leon.ui.screens.settings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.svenjacobs.app.leon.BuildConfig
import com.svenjacobs.app.leon.R
import com.svenjacobs.app.leon.ui.screens.settings.model.SettingsScreenViewModel
import com.svenjacobs.app.leon.ui.theme.AppTheme

@Composable
fun SettingsScreen(
	onHideBars: (Boolean) -> Unit,
	modifier: Modifier = Modifier,
	viewModel: SettingsScreenViewModel = viewModel(),
) {
	val uiState by viewModel.uiState.collectAsStateWithLifecycle()
	val navController = rememberNavController()

	NavHost(
		modifier = modifier,
		navController = navController,
		startDestination = SCREEN_SETTINGS,
	) {
		composable(
			route = SCREEN_SETTINGS,
		) {
			LaunchedEffect(Unit) { onHideBars(false) }

			Content(
				browserEnabled = uiState.browserEnabled,
				onSanitizersClick = { navController.navigate(SCREEN_SANITIZERS) },
				onLicensesClick = { navController.navigate(SCREEN_LICENSES) },
				onBrowserSwitchCheckedChange = viewModel::onBrowserSwitchCheckedChange,
			)
		}

		composable(
			route = SCREEN_SANITIZERS,
		) {
			LaunchedEffect(Unit) { onHideBars(true) }

			SettingsSanitizersScreen(
				viewModel = viewModel(),
				onBackClick = { navController.popBackStack() },
			)
		}

		composable(
			route = SCREEN_LICENSES,
		) {
			LaunchedEffect(Unit) { onHideBars(true) }

			SettingsLicensesScreen(
				onBackClick = { navController.popBackStack() },
			)
		}
	}
}

@Composable
private fun Content(
	browserEnabled: Boolean?,
	onSanitizersClick: () -> Unit,
	onLicensesClick: () -> Unit,
	onBrowserSwitchCheckedChange: (Boolean) -> Unit,
	modifier: Modifier = Modifier,
) {
	Box(
		modifier = modifier.fillMaxSize(),
	) {
		if (browserEnabled == null) {
			CircularProgressIndicator(
				modifier = Modifier.align(Alignment.Center),
			)
		} else {
			Column(
				modifier = Modifier.padding(16.dp),
			) {
				OutlinedButton(
					modifier = Modifier.fillMaxWidth(),
					onClick = onSanitizersClick,
				) {
					Text(stringResource(R.string.sanitizers))
				}

				OutlinedButton(
					modifier = Modifier
						.fillMaxWidth()
						.padding(top = 16.dp),
					onClick = onLicensesClick,
				) {
					Text(stringResource(R.string.licenses))
				}

				Row(
					modifier = Modifier
						.fillMaxWidth()
						.padding(top = 16.dp),
					verticalAlignment = Alignment.CenterVertically,
				) {
					Text(
						modifier = Modifier
							.padding(end = 8.dp)
							.weight(1f),
						text = stringResource(R.string.register_as_browser),
					)

					Switch(
						checked = browserEnabled,
						onCheckedChange = onBrowserSwitchCheckedChange,
					)
				}
			}
		}

		Text(
			modifier = Modifier
				.align(Alignment.BottomEnd)
				.padding(
					bottom = 8.dp,
					end = 8.dp,
				),
			text = BuildConfig.VERSION_NAME,
			style = MaterialTheme.typography.bodySmall,
		)
	}
}

@Composable
@Preview
private fun SettingsScreenPreview() {
	AppTheme {
		SettingsScreen(
			onHideBars = {},
		)
	}
}

private const val SCREEN_SETTINGS = "settings"
private const val SCREEN_SANITIZERS = "sanitizers"
private const val SCREEN_LICENSES = "licenses"
