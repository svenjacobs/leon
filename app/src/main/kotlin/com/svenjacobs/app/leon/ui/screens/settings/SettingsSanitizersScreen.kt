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

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.svenjacobs.app.leon.R
import com.svenjacobs.app.leon.ui.common.views.TopAppBar
import com.svenjacobs.app.leon.ui.screens.settings.model.SettingsSanitizersScreenViewModel

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun SettingsSanitizersScreen(
    modifier: Modifier = Modifier,
    viewModel: SettingsSanitizersScreenViewModel,
    onBackClick: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                onBackClick = onBackClick,
            )
        }
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .padding(contentPadding)
                .padding(16.dp)
                .navigationBarsPadding()
        ) {
            Text(
                modifier = Modifier.padding(
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 16.dp
                ),
                text = stringResource(R.string.sanitizers_description),
                style = MaterialTheme.typography.bodyLarge,
            )

            Card {
                LazyColumn {
                    uiState.sanitizers.forEach { sanitizer ->
                        item(key = sanitizer.id.value) {
                            Item(
                                name = sanitizer.name,
                                isEnabled = sanitizer.enabled,
                                onCheckedChange = { enabled ->
                                    viewModel.onSanitizerCheckedChange(sanitizer.id, enabled)
                                },
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun Item(
    modifier: Modifier = Modifier,
    name: String,
    isEnabled: Boolean,
    onCheckedChange: (Boolean) -> Unit,
) {
    Row(
        modifier = modifier.padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            modifier = Modifier.weight(2f),
            text = name,
        )

        Switch(
            checked = isEnabled,
            onCheckedChange = onCheckedChange,
        )
    }
}
