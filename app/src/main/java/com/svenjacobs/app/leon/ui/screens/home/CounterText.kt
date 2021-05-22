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
