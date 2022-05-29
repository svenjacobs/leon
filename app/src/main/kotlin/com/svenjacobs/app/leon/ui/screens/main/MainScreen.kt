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

import android.content.Context
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.svenjacobs.app.leon.BuildConfig
import com.svenjacobs.app.leon.R
import com.svenjacobs.app.leon.services.model.CleaningResult
import com.svenjacobs.app.leon.ui.screens.home.HomeScreen
import com.svenjacobs.app.leon.ui.screens.main.model.MainViewModel
import com.svenjacobs.app.leon.ui.screens.main.model.Screen
import com.svenjacobs.app.leon.ui.screens.settings.SettingsParametersScreen
import com.svenjacobs.app.leon.ui.screens.settings.SettingsScreen
import com.svenjacobs.app.leon.ui.theme.AppTheme

@Composable
fun MainScreen(
    context: Context,
    viewModel: MainViewModel,
) {
    val result by viewModel.result.collectAsState()

    fun onShareButtonClick(result: CleaningResult.Success) {
        val intent = viewModel.buildIntent(result.cleanedText)
        context.startActivity(intent)
    }

    fun onVerifyButtonClick(result: CleaningResult.Success) {
        val intent = viewModel.buildCustomTabIntent(context)
        intent.launchUrl(context, Uri.parse(result.urls.first()))
    }

    val navController = rememberNavController()
    val isBackVisible by viewModel.isBackVisible.collectAsState()

    AppTheme {
        Scaffold(
            topBar = { MyTopAppBar(isBackVisible, navController) },
            bottomBar = { MyBottomBar(navController) },
            content = { padding ->
                Box(
                    modifier = Modifier.padding(
                        start = padding.calculateStartPadding(layoutDirection = LocalLayoutDirection.current),
                        top = padding.calculateTopPadding(),
                        end = padding.calculateEndPadding(layoutDirection = LocalLayoutDirection.current),
                        bottom = padding.calculateBottomPadding(),
                    )
                ) {
                    BackgroundImage()

                    NavHost(
                        navController = navController,
                        startDestination = Screen.Home.route,
                    ) {
                        composable(Screen.Home.route) {
                            viewModel.setIsBackVisible(false)
                            HomeScreen(
                                result = result,
                                onShareButtonClick = ::onShareButtonClick,
                                onVerifyButtonClick = ::onVerifyButtonClick,
                            )
                        }

                        composable(Screen.Settings.route) {
                            viewModel.setIsBackVisible(false)
                            SettingsScreen(
                                viewModel = hiltViewModel(),
                                navController = navController,
                            )
                        }

                        composable(Screen.SettingsParameters.route) {
                            viewModel.setIsBackVisible(true)
                            SettingsParametersScreen(
                                viewModel = hiltViewModel(),
                            )
                        }
                    }
                }
            },
        )
    }
}

@Composable
private fun MyTopAppBar(
    isBackVisible: Boolean,
    navController: NavHostController
) {
    Box(modifier = Modifier.background(color = MaterialTheme.colors.primary)) {
        TopAppBar(
            modifier = Modifier.statusBarsPadding(),
            elevation = 0.dp,
            backgroundColor = MaterialTheme.colors.primary,
            title = {
                Text(
                    text = stringResource(R.string.scaffold_title),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            },
            navigationIcon = if (isBackVisible) ({ NavigationIcon(navController) }) else null
        )
    }
}

@Composable
private fun MyBottomBar(
    navController: NavHostController
) {
    val bottomNavItems = listOf(
        Screen.Home,
        Screen.Settings
    )

    Box(
        modifier = Modifier.background(color = MaterialTheme.colors.primary)
    ) {
        BottomNavigation(
            modifier = Modifier.navigationBarsPadding(),
            elevation = 0.dp,
        ) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination

            bottomNavItems.forEach { screen ->
                BottomNavigationItem(
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
}

@Composable
private fun NavigationIcon(navController: NavController) {
    IconButton(onClick = { navController.popBackStack() }) {
        Icon(
            Icons.Filled.ArrowBack,
            contentDescription = stringResource(R.string.a11y_back_navigation),
        )
    }
}

@Composable
private fun BackgroundImage() {
    Image(
        painter = painterResource(if (BuildConfig.DEBUG) R.drawable.background_bug else R.drawable.background_broom),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxSize(),
    )
}
