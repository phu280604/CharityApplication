package com.developing.charityapplication.presentation.view.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = Black,
    onPrimary = White,
    secondary = Green,
    onSecondary = Black,
    secondaryContainer = Black,
    onSecondaryContainer = White,
    background = LightBlue.copy(alpha = 0.72f),
    onBackground = LightBlue,
    surface = GreenGray,
    onSurface = Gray,
    error = Black,
    onError = Red
)

private val LightColorScheme = lightColorScheme(
    primary = White,
    onPrimary = Black,
    secondary = Green,
    onSecondary = White,
    secondaryContainer = Black,
    onSecondaryContainer = White,
    background = LightBlue.copy(alpha = 0.72f),
    onBackground = LightBlue,
    surface = GreenGray,
    onSurface = Gray,
    error = White,
    onError = Red
)

var AppColorTheme: ColorScheme = LightColorScheme
    private set

@Composable
fun HeartBellTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val AppColorTheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = AppColorTheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = AppColorTheme,
        typography = AppTypography,
        content = content
    )
}