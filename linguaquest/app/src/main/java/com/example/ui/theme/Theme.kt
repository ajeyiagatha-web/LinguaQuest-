package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LinguaLightColorScheme = lightColorScheme(
    primary = BrandPrimary,
    onPrimary = Color.White,
    primaryContainer = BrandPrimaryContainer,
    onPrimaryContainer = Color(0xFFF4EEFF),
    inversePrimary = BrandPrimaryFixedDim,
    
    secondary = BrandSecondary,
    onSecondary = Color.White,
    secondaryContainer = BrandSecondaryContainer,
    onSecondaryContainer = OnBrandSecondaryContainer,
    
    tertiary = BrandTertiary,
    onTertiary = Color.White,
    tertiaryContainer = BrandTertiaryContainer,
    onTertiaryContainer = Color(0xFFFFEEDC),
    
    background = SurfaceBackground,
    onBackground = TextOnSurface,
    surface = SurfaceBackground,
    onSurface = TextOnSurface,
    surfaceVariant = SurfaceContainerHighest,
    onSurfaceVariant = TextOnSurfaceVariant,
    
    outline = OutlineColor,
    outlineVariant = OutlineVariantColor,
    
    error = Color(0xFFBA1A1A),
    onError = Color.White,
    errorContainer = Color(0xFFFFDAD6),
    onErrorContainer = Color(0xFF93000A)
)

@Composable
fun LinguaQuestTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LinguaLightColorScheme,
        typography = AppTypography,
        content = content
    )
}
