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

package com.svenjacobs.app.leon.ui.screens.home

import androidx.annotation.PluralsRes
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateInt
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import com.svenjacobs.app.leon.ui.theme.primaryDarkColor

@Composable
fun CounterText(
    number: Int,
    modifier: Modifier = Modifier,
    @PluralsRes pluralsRes: Int,
    style: TextStyle = LocalTextStyle.current,
) {
    val state = remember { MutableTransitionState(0) }
    state.targetState = number
    val transition = updateTransition(state, label = "counter")

    val currentNumber by transition.animateInt(
        transitionSpec = {
            tween(
                durationMillis = 1000,
                delayMillis = 500,
            )
        },
        label = "currentNumber"
    ) { it }

    val color by transition.animateColor(
        transitionSpec = {
            tween(
                durationMillis = 1000,
                delayMillis = 500,
            )
        },
        label = "color"
    ) {
        if (it == number) primaryDarkColor else MaterialTheme.colors.onSurface
    }

    val text = LocalContext.current.resources.getQuantityString(
        pluralsRes,
        currentNumber,
        currentNumber
    )

    Text(
        text = text,
        modifier = modifier,
        style = style.copy(color = color),
    )
}
