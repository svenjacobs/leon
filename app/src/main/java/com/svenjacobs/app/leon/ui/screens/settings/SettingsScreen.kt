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

package com.svenjacobs.app.leon.ui.screens.settings

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.mikepenz.aboutlibraries.LibsBuilder
import com.svenjacobs.app.leon.BuildConfig
import com.svenjacobs.app.leon.R
import com.svenjacobs.app.leon.ui.screens.main.model.Screen
import com.svenjacobs.app.leon.ui.screens.settings.model.SettingsViewModel

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel,
    navController: NavController,
) {
    fun onParametersClick() {
        navController.navigate(Screen.SettingsParameters.route)
    }

    fun onLicensesClick(context: Context) {
        LibsBuilder()
            .withAboutIconShown(false)
            .withVersionShown(false)
            .start(context)
    }

    Column(
        modifier = Modifier.padding(16.dp),
    ) {

        OutlinedButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = ::onParametersClick
        ) {
            Text(stringResource(R.string.parameters))
        }

        val context = LocalContext.current

        OutlinedButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            onClick = { onLicensesClick(context) },
        ) {
            Text(stringResource(R.string.licenses))
        }
    }

    VersionNumber()
}

@Composable
private fun VersionNumber() {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val button = createRef()

        Text(
            modifier = Modifier.constrainAs(button) {
                bottom.linkTo(parent.bottom, margin = 8.dp)
                end.linkTo(parent.end, margin = 8.dp)
            },
            text = BuildConfig.VERSION_NAME,
            style = MaterialTheme.typography.body2,
        )
    }
}
