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
