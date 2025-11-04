package com.nayibit.phrasalito_presentation.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color


// Main Colors (based on #040794)
val BluePrimary = Color(0xFF040794)
val BluePrimaryContainer = Color(0xFFDBE0FF)
val BlueOnPrimary = Color.White
val BlueOnPrimaryContainer = Color(0xFF000258)

val Secondary = Color(0xFF5C5D72)

val Error = Color(0xFFBA1A1A)
val OnError = Color.White

val primaryGradientStart: Color = Color(0xFF1ca9c9)
val primaryGradientEnd: Color = Color(0xFF0047ab)
val progressBackground: Color = Color(0xFFE0E0E0)
val badgeNew: Brush = Brush.horizontalGradient(
    colors = listOf(primaryGradientStart, primaryGradientEnd)
)
val badgeComplete: Brush = Brush.horizontalGradient(
    colors = listOf(Color(0xFF10B981), Color(0xFF059669))
)
