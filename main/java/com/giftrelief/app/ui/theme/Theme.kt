package com.giftrelief.app.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    primary = GiftGreen,
    onPrimary = Color.White,
    secondary = GiftGreenDark,
    background = GiftBackground,
    surface = GiftSurface,
    error = GiftError,
    onBackground = GiftTextPrimary,
    onSurface = GiftTextPrimary
)

private val DarkColors = darkColorScheme(
    primary = GiftGreen,
    onPrimary = Color.White,
    secondary = GiftGreenDark,
    background = Color(0xFF121212),
    surface = Color(0xFF1E1E1E)
)

@Composable
fun GiftReliefTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColors else LightColors
    MaterialTheme(
        colorScheme = colors,
        typography = GiftReliefTypography,
        content = content
    )
}
