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
