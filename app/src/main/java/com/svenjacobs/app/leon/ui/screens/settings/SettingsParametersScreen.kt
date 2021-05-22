package com.svenjacobs.app.leon.ui.screens.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.svenjacobs.app.leon.R
import com.svenjacobs.app.leon.domain.model.Sanitizer
import com.svenjacobs.app.leon.ui.screens.settings.model.SettingsParametersViewModel

@Composable
fun SettingsParametersScreen(
    viewModel: SettingsParametersViewModel,
) {
    val sanitizers by viewModel.sanitizers.collectAsState(initial = emptyList())

    Card(
        modifier = Modifier.padding(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(
                start = 8.dp,
                top = 8.dp,
                end = 8.dp,
                bottom = 16.dp,
            )
        ) {
            Text(
                stringResource(R.string.parameters_description),
                modifier = Modifier.padding(8.dp),
                style = MaterialTheme.typography.body1,
            )

            LazyColumn {
                sanitizers.forEach { sanitizer ->
                    item(key = sanitizer.uid) {
                        Item(
                            sanitizer = sanitizer,
                            viewModel = viewModel,
                        )
                    }
                }
            }
        }

    }
}

@Composable
private fun Item(
    sanitizer: Sanitizer,
    viewModel: SettingsParametersViewModel,
) {
    Row(
        modifier = Modifier.padding(16.dp)
    ) {
        Text(
            sanitizer.name,
            modifier = Modifier.weight(2F),
        )
        Switch(
            checked = sanitizer.isEnabled,
            onCheckedChange = { enabled ->
                viewModel.setEnabled(sanitizer, enabled)
            },
        )
    }
}
