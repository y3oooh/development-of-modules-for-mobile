package com.example.sonora.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

fun Modifier.shimmer(shape: Shape = RoundedCornerShape(8.dp)): Modifier = composed {
    val base = MaterialTheme.colorScheme.surfaceVariant
    // Явный контрастный блик: на светлой теме — белый, на тёмной — светлее поверхности
    val highlight = Color.White.copy(alpha = 0.85f)

    val transition = rememberInfiniteTransition(label = "shimmer")
    val progress by transition.animateFloat(
        initialValue = -600f,
        targetValue = 1800f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1400, easing = LinearEasing),
            repeatMode = RepeatMode.Restart,
        ),
        label = "shimmer_progress",
    )

    background(
        brush = Brush.linearGradient(
            colors = listOf(base, base, highlight, base, base),
            start = Offset(progress, 0f),
            end = Offset(progress + 600f, 0f),
        ),
        shape = shape,
    )
}