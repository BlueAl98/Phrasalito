package com.nayibit.utils.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary                = Dark_Primary,
    onPrimary              = Dark_OnPrimary,
    primaryContainer       = Dark_PrimaryContainer,
    onPrimaryContainer     = Dark_OnPrimaryContainer,
    inversePrimary         = Dark_InversePrimary,
    secondary              = Dark_Secondary,
    onSecondary            = Dark_OnSecondary,
    secondaryContainer     = Dark_SecondaryContainer,
    onSecondaryContainer   = Dark_OnSecondaryContainer,
    tertiary               = Dark_Tertiary,
    onTertiary             = Dark_OnTertiary,
    tertiaryContainer      = Dark_TertiaryContainer,
    onTertiaryContainer    = Dark_OnTertiaryContainer,
    error                  = Dark_Error,
    onError                = Dark_OnError,
    errorContainer         = Dark_ErrorContainer,
    onErrorContainer       = Dark_OnErrorContainer,
    background             = Dark_Background,
    onBackground           = Dark_OnSurface,
    surface                = Dark_Surface,
    onSurface              = Dark_OnSurface,
    surfaceVariant         = Dark_SurfaceVariant,
    onSurfaceVariant       = Dark_OnSurfaceVariant,
    inverseSurface         = Dark_InverseSurface,
    inverseOnSurface       = Dark_InverseOnSurface,
    outline                = Dark_Outline,
    outlineVariant         = Dark_OutlineVariant,
    surfaceTint            = Dark_SurfaceTint,
    scrim                  = Dark_Background,
)

private val LightColorScheme = lightColorScheme(
    primary                = Light_Primary,
    onPrimary              = Light_OnPrimary,
    primaryContainer       = Light_PrimaryContainer,
    onPrimaryContainer     = Light_OnPrimaryContainer,
    inversePrimary         = Light_InversePrimary,
    secondary              = Light_Secondary,
    onSecondary            = Light_OnSecondary,
    secondaryContainer     = Light_SecondaryContainer,
    onSecondaryContainer   = Light_OnSecondaryContainer,
    tertiary               = Light_Tertiary,
    onTertiary             = Light_OnTertiary,
    tertiaryContainer      = Light_TertiaryContainer,
    onTertiaryContainer    = Light_OnTertiaryContainer,
    error                  = Light_Error,
    onError                = Light_OnError,
    errorContainer         = Light_ErrorContainer,
    onErrorContainer       = Light_OnErrorContainer,
    background             = Light_Background,
    onBackground           = Light_OnSurface,
    surface                = Light_Surface,
    onSurface              = Light_OnSurface,
    surfaceVariant         = Light_SurfaceVariant,
    onSurfaceVariant       = Light_OnSurfaceVariant,
    inverseSurface         = Light_InverseSurface,
    inverseOnSurface       = Light_InverseOnSurface,
    outline                = Light_Outline,
    outlineVariant         = Light_OutlineVariant,
    surfaceTint            = Light_SurfaceTint,
    scrim                  = Dark_Background,
)

@Composable
fun PhrasalitoTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
