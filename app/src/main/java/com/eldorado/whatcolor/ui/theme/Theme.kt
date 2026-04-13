package com.eldorado.whatcolor.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = AppOnSurface,
    secondary = AppSecondary,
    background = AppBackground,
    surface = AppSurface,
    onPrimary = AppBackground,
    onSecondary = AppBackground,
    onBackground = AppOnSurface,
    onSurface = AppOnSurface,
)

@Composable
fun WhatcolorTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = Typography,
        content = content
    )
}
