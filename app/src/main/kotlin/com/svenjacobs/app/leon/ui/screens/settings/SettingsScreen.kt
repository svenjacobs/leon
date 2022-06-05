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

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mikepenz.aboutlibraries.LibsBuilder
import com.svenjacobs.app.leon.BuildConfig
import com.svenjacobs.app.leon.R
import com.svenjacobs.app.leon.ui.screens.settings.model.SettingsViewModel
import com.svenjacobs.app.leon.ui.theme.AppTheme

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = viewModel(),
    onParametersClick: () -> Unit,
) {
    val context = LocalContext.current

    fun onLicensesClick() {
        LibsBuilder()
            .withAboutIconShown(false)
            .withVersionShown(false)
            .start(context)
    }

    Content(
        onParametersClick = onParametersClick,
        onLicensesClick = ::onLicensesClick,
    )
}

@Composable
private fun Content(
    modifier: Modifier = Modifier,
    onParametersClick: () -> Unit,
    onLicensesClick: () -> Unit,
) {
    Box(
        modifier = modifier.fillMaxSize(),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
        ) {
            OutlinedButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = onParametersClick
            ) {
                Text(stringResource(R.string.parameters))
            }

            OutlinedButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                onClick = onLicensesClick,
            ) {
                Text(stringResource(R.string.licenses))
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
            style = MaterialTheme.typography.body2,
        )
    }
}

@Composable
@Preview
private fun SettingsScreenPreview() {
    AppTheme {
        SettingsScreen(
            onParametersClick = {},
        )
    }
}
