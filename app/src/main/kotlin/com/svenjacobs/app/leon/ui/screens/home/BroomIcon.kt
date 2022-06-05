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

package com.svenjacobs.app.leon.ui.screens.home

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.svenjacobs.app.leon.R

enum class BroomState { START, END }

@Composable
fun BroomIcon(
    modifier: Modifier = Modifier,
) {
    val state = remember { MutableTransitionState(BroomState.START) }
    state.targetState = BroomState.END
    val transition = updateTransition(state, label = "broom")

    val color by transition.animateColor(
        transitionSpec = {
            tween(
                durationMillis = 1000,
                delayMillis = 250,
            )
        },
        label = "color"
    ) { progress ->
        when (progress) {
            BroomState.START -> Color.DarkGray
            BroomState.END -> MaterialTheme.colorScheme.primary
        }
    }

    val scale by transition.animateFloat(
        transitionSpec = {
            tween(
                durationMillis = 1000,
                delayMillis = 250,
            )
        },
        label = "scale"
    ) { progress ->
        when (progress) {
            BroomState.START -> 0.8F
            BroomState.END -> 1.0F
        }
    }

    Image(
        modifier = modifier.scale(scale),
        painter = painterResource(R.drawable.ic_broom),
        contentDescription = stringResource(R.string.a11y_broom_icon),
        colorFilter = ColorFilter.tint(color),
    )
}
