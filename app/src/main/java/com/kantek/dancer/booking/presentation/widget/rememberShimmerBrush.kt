package com.kantek.dancer.booking.presentation.widget

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

@Composable
fun rememberShimmerBrush(durationMillis: Int = 500): Brush {
    val shimmerColors = listOf(
        Color.LightGray.copy(alpha = 0.6f),
        Color.Gray.copy(alpha = 0.2f),
        Color.LightGray.copy(alpha = 0.6f)
    )

    val transition = rememberInfiniteTransition()
    val translateAnim = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = durationMillis, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    return Brush.linearGradient(
        colors = shimmerColors,
        start = Offset(x = translateAnim.value, y = 0f),
        end = Offset(x = translateAnim.value + 300f, y = 0f)
    )
}