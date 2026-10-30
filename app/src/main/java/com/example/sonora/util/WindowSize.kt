package com.example.sonora.util

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

enum class WidthBucket { Compact, Medium, Expanded }

fun WindowSizeClass.widthBucket(): WidthBucket = when (widthSizeClass) {
    WindowWidthSizeClass.Compact -> WidthBucket.Compact
    WindowWidthSizeClass.Medium -> WidthBucket.Medium
    else -> WidthBucket.Expanded
}

fun WidthBucket.horizontalPadding(): Dp = when (this) {
    WidthBucket.Compact -> 16.dp
    WidthBucket.Medium -> 32.dp
    WidthBucket.Expanded -> 48.dp
}

fun WidthBucket.maxContentWidth(): Dp = when (this) {
    WidthBucket.Compact -> Dp.Unspecified
    WidthBucket.Medium -> 640.dp
    WidthBucket.Expanded -> 840.dp
}