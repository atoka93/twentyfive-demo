package com.attilaszabo.twentyfivedemo.common.presentation.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

private val GreyLightPrimary = Color(0xFF616161)
private val GreyLightOnPrimary = Color(0xFFFFFFFF)
private val GreyLightPrimaryContainer = Color(0xFFE0E0E0)
private val GreyLightOnPrimaryContainer = Color(0xFF000000)

private val GreyDarkPrimary = Color(0xFF9E9E9E)
private val GreyDarkOnPrimary = Color(0xFF000000)
private val GreyDarkPrimaryContainer = Color(0xFF424242)
private val GreyDarkOnPrimaryContainer = Color(0xFFFFFFFF)

val GoldenBrown = Color(0xFFDAA520)

val DarkGreen = Color(0xFF00B700)
val LightGreen = Color(0xFF59D900)
val LightOrange = Color(0xFFFFC107)
val DarkOrange = Color(0xFFFF9800)
val Red = Color(0xFFE80D00)

val LightColorScheme = lightColorScheme(
    primary = GreyLightPrimary,
    onPrimary = GreyLightOnPrimary,
    primaryContainer = GreyLightPrimaryContainer,
    onPrimaryContainer = GreyLightOnPrimaryContainer,
    secondary = GreyLightPrimary,
    onSecondary = GreyLightOnPrimary,
    background = Color(0xFFF5F5F5), // Grey 100 background
    onBackground = Color.Black,
    surface = Color.White,
    onSurface = Color.Black,
    error = Color(0xFFB00020),
    onError = Color.White
)

val DarkColorScheme = darkColorScheme(
    primary = GreyDarkPrimary,
    onPrimary = GreyDarkOnPrimary,
    primaryContainer = GreyDarkPrimaryContainer,
    onPrimaryContainer = GreyDarkOnPrimaryContainer,
    secondary = GreyDarkPrimary,
    onSecondary = GreyDarkOnPrimary,
    background = Color(0xFF121212),
    onBackground = Color.White,
    surface = Color(0xFF1E1E1E),
    onSurface = Color.White,
    error = Color(0xFFCF6679),
    onError = Color.Black
)
