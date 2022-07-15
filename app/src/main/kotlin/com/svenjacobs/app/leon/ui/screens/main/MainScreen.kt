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

package com.svenjacobs.app.leon.ui.screens.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.svenjacobs.app.leon.BuildConfig
import com.svenjacobs.app.leon.R
import com.svenjacobs.app.leon.ui.common.MyTopAppBar
import com.svenjacobs.app.leon.ui.screens.home.HomeScreen
import com.svenjacobs.app.leon.ui.screens.main.model.MainScreenViewModel
import com.svenjacobs.app.leon.ui.screens.main.model.Screen
import com.svenjacobs.app.leon.ui.screens.settings.SettingsScreen
import com.svenjacobs.app.leon.ui.theme.AppTheme
import kotlinx.coroutines.launch

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun MainScreen(
    modifier: Modifier = Modifier,
    viewModel: MainScreenViewModel,
) {
    val state by viewModel.uiState.collectAsState()

    var hideBars by rememberSaveable { mutableStateOf(false) }
    val navController = rememberNavController()
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val systemUiController = rememberSystemUiController()
    val isDarkTheme = isSystemInDarkTheme()

    LaunchedEffect(Unit) {
        systemUiController.setStatusBarColor(Color.Transparent, darkIcons = !isDarkTheme)
    }

    AppTheme {
        Scaffold(
            modifier = modifier,
            topBar = { if (!hideBars) MyTopAppBar() },
            bottomBar = { if (!hideBars) MyBottomBar(navController = navController) },
            snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
            content = { padding ->
                Box(
                    modifier = Modifier.padding(padding)
                ) {
                    BackgroundImage()

                    NavHost(
                        navController = navController,
                        startDestination = Screen.Home.route,
                    ) {
                        composable(Screen.Home.route) {
                            HomeScreen(
                                viewModel = hiltViewModel(),
                                result = state.result,
                                showSnackbarMessage = { message ->
                                    coroutineScope.launch {
                                        snackbarHostState.showSnackbar(message)
                                    }
                                },
                            )
                        }

                        composable(Screen.Settings.route) {
                            SettingsScreen(
                                onHideBars = { hideBars = it },
                            )
                        }
                    }
                }
            },
        )
    }
}

@Composable
private fun MyBottomBar(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    val bottomNavItems = listOf(
        Screen.Home,
        Screen.Settings
    )

    NavigationBar(
        modifier = modifier.navigationBarsPadding(),
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        bottomNavItems.forEach { screen ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = screen.icon,
                        contentDescription = stringResource(screen.iconContentDescription),
                    )
                },
                label = { Text(stringResource(screen.label)) },
                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
            )
        }
    }
}

@Composable
private fun BackgroundImage(
    modifier: Modifier = Modifier,
) {
    Image(
        modifier = modifier.fillMaxSize(),
        painter = painterResource(if (BuildConfig.DEBUG) R.drawable.background_bug else R.drawable.background_broom),
        contentDescription = null,
        contentScale = ContentScale.Crop,
    )
}
