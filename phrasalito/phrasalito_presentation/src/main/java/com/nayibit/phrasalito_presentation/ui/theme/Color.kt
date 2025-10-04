package com.nayibit.phrasalito_presentation.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)


// Main Colors (based on #040794)
val BluePrimary = Color(0xFF040794)
val BluePrimaryContainer = Color(0xFFDBE0FF)
val BlueOnPrimary = Color.White
val BlueOnPrimaryContainer = Color(0xFF000258)

val Secondary = Color(0xFF5C5D72)
val Tertiary = Color(0xFF78536B)

val Error = Color(0xFFBA1A1A)
val OnError = Color.White
val ErrorContainer = Color(0xFFFFDAD6)
val OnErrorContainer = Color(0xFF93000A)

// Optional: Success color (not in Material default)
val Success = Color(0xFF4CAF50) // Green 500
val OnSuccess = Color.White

// Deck Colors
val primaryGradientStart: Color = Color(0xFF667EEA)
val primaryGradientEnd: Color = Color(0xFF764BA2)
val cardBackground: Color = Color.White
val textPrimary: Color = Color(0xFF1A1A1A)
val textSecondary: Color = Color(0xFF666666)
val progressBackground: Color = Color(0xFFE0E0E0)
val badgeNew: Brush = Brush.horizontalGradient(
    colors = listOf(Color(0xFF667EEA), Color(0xFF764BA2))
)
val badgeComplete: Brush = Brush.horizontalGradient(
    colors = listOf(Color(0xFF10B981), Color(0xFF059669))
)