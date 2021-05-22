package com.svenjacobs.app.leon.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = darkPrimaryColor,
    primaryVariant = darkPrimaryDarkColor,
    secondary = darkPrimaryColor,
    secondaryVariant = darkPrimaryDarkColor,
    background = darkBackgroundColor,
)

private val LightColorPalette = lightColors(
    primary = primaryColor,
    primaryVariant = primaryDarkColor,
    secondary = primaryColor,
    secondaryVariant = primaryDarkColor,
    background = backgroundColor,
)

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colors = when (darkTheme) {
        true -> DarkColorPalette
        false -> LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = typography,
        shapes = shapes,
        content = content
    )
}
