package com.svenjacobs.app.leon.ui.screens.home

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import com.svenjacobs.app.leon.R
import com.svenjacobs.app.leon.ui.theme.primaryColor

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
            BroomState.END -> primaryColor
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
        contentDescription = null,
        colorFilter = ColorFilter.tint(color),
    )
}
