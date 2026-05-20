package com.nayibit.utils.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

// =============================================================
// DESIGN SYSTEM: Deep Violet (single-color — same for both themes)
// Change values here to update the entire app.
// =============================================================

// --- Shared background / surface / text tokens ---
// These are used identically in both DarkColorScheme and LightColorScheme.

val ColorBackground              = Color(0xFF120B32)   // deep navy-purple page bg
val ColorSurface                 = Color(0xFF120B32)
val ColorSurfaceDim              = Color(0xFF0A0720)
val ColorSurfaceBright           = Color(0xFF3D2E82)
val ColorSurfaceContainerLowest  = Color(0xFF0D0930)
val ColorSurfaceContainerLow     = Color(0xFF201860)   // card layer 1
val ColorSurfaceContainer        = Color(0xFF2A1E72)   // card layer 2
val ColorSurfaceContainerHigh    = Color(0xFF342880)   // card layer 3 — language cards
val ColorSurfaceContainerHighest = Color(0xFF3D308C)   // card layer 4
val ColorSurfaceVariant          = Color(0xFF362A7E)
val ColorOnSurface               = Color(0xFFEDE8FF)   // off-white lavender text
val ColorOnSurfaceVariant        = Color(0xFFC8BDEE)   // muted lavender text
val ColorInverseSurface          = Color(0xFFEDE8FF)
val ColorInverseOnSurface        = Color(0xFF32295A)
val ColorOutline                 = Color(0xFF9585C8)
val ColorOutlineVariant          = Color(0xFF4A3D7A)

// --- Primary (brand / action) ---
val ColorPrimary                 = Color(0xFFCEBEFF)   // light lavender label
val ColorOnPrimary               = Color(0xFF330095)
val ColorPrimaryContainer        = Color(0xFF7860E8)   // vivid violet — buttons / FAB
val ColorOnPrimaryContainer      = Color(0xFFEDE8FF)
val ColorInversePrimary          = Color(0xFF5B3ECB)

// --- Secondary ---
val ColorSecondary               = Color(0xFF7860E8).copy(alpha = 0.22f)  // vivid violet 22% — card bg
val ColorOnSecondary             = Color(0xFF2B2342)
val ColorSecondaryContainer      = Color(0xFF423B5A)
val ColorOnSecondaryContainer    = Color(0xFFD8D0EE)

// --- Tertiary (blue accent) ---
val ColorTertiary                = Color(0xFF9DCFFF)
val ColorOnTertiary              = Color(0xFF003256)
val ColorTertiaryContainer       = Color(0xFF2B5CA0)
val ColorOnTertiaryContainer     = Color(0xFFD4EAFF)

// --- Error ---
val ColorError                   = Color(0xFFFFB4AB)
val ColorOnError                 = Color(0xFF690005)
val ColorErrorContainer          = Color(0xFF93000A)
val ColorOnErrorContainer        = Color(0xFFFFDAD6)

// --- Surface tint ---
val ColorSurfaceTint             = Color(0xFFCEBEFF)

// =============================================================
// Both themes use the same tokens (single-color design).
// =============================================================

// Dark scheme tokens (kept as aliases so Theme.kt compiles unchanged)
val Dark_Background              = ColorBackground
val Dark_Surface                 = ColorSurface
val Dark_SurfaceDim              = ColorSurfaceDim
val Dark_SurfaceBright           = ColorSurfaceBright
val Dark_SurfaceContainerLowest  = ColorSurfaceContainerLowest
val Dark_SurfaceContainerLow     = ColorSurfaceContainerLow
val Dark_SurfaceContainer        = ColorSurfaceContainer
val Dark_SurfaceContainerHigh    = ColorSurfaceContainerHigh
val Dark_SurfaceContainerHighest = ColorSurfaceContainerHighest
val Dark_SurfaceVariant          = ColorSurfaceVariant
val Dark_OnSurface               = ColorOnSurface
val Dark_OnSurfaceVariant        = ColorOnSurfaceVariant
val Dark_InverseSurface          = ColorInverseSurface
val Dark_InverseOnSurface        = ColorInverseOnSurface
val Dark_Outline                 = ColorOutline
val Dark_OutlineVariant          = ColorOutlineVariant
val Dark_Primary                 = ColorPrimary
val Dark_OnPrimary               = ColorOnPrimary
val Dark_PrimaryContainer        = ColorPrimaryContainer
val Dark_OnPrimaryContainer      = ColorOnPrimaryContainer
val Dark_InversePrimary          = ColorInversePrimary
val Dark_Secondary               = ColorSecondary
val Dark_OnSecondary             = ColorOnSecondary
val Dark_SecondaryContainer      = ColorSecondaryContainer
val Dark_OnSecondaryContainer    = ColorOnSecondaryContainer
val Dark_Tertiary                = ColorTertiary
val Dark_OnTertiary              = ColorOnTertiary
val Dark_TertiaryContainer       = ColorTertiaryContainer
val Dark_OnTertiaryContainer     = ColorOnTertiaryContainer
val Dark_Error                   = ColorError
val Dark_OnError                 = ColorOnError
val Dark_ErrorContainer          = ColorErrorContainer
val Dark_OnErrorContainer        = ColorOnErrorContainer
val Dark_SurfaceTint             = ColorSurfaceTint

// Light scheme tokens — identical to dark (single-color design)
val Light_Background              = ColorBackground
val Light_Surface                 = ColorSurface
val Light_SurfaceDim              = ColorSurfaceDim
val Light_SurfaceBright           = ColorSurfaceBright
val Light_SurfaceContainerLowest  = ColorSurfaceContainerLowest
val Light_SurfaceContainerLow     = ColorSurfaceContainerLow
val Light_SurfaceContainer        = ColorSurfaceContainer
val Light_SurfaceContainerHigh    = ColorSurfaceContainerHigh
val Light_SurfaceContainerHighest = ColorSurfaceContainerHighest
val Light_SurfaceVariant          = ColorSurfaceVariant
val Light_OnSurface               = ColorOnSurface
val Light_OnSurfaceVariant        = ColorOnSurfaceVariant
val Light_InverseSurface          = ColorInverseSurface
val Light_InverseOnSurface        = ColorInverseOnSurface
val Light_Outline                 = ColorOutline
val Light_OutlineVariant          = ColorOutlineVariant
val Light_Primary                 = ColorPrimary
val Light_OnPrimary               = ColorOnPrimary
val Light_PrimaryContainer        = ColorPrimaryContainer
val Light_OnPrimaryContainer      = ColorOnPrimaryContainer
val Light_InversePrimary          = ColorInversePrimary
val Light_Secondary               = ColorSecondary
val Light_OnSecondary             = ColorOnSecondary
val Light_SecondaryContainer      = ColorSecondaryContainer
val Light_OnSecondaryContainer    = ColorOnSecondaryContainer
val Light_Tertiary                = ColorTertiary
val Light_OnTertiary              = ColorOnTertiary
val Light_TertiaryContainer       = ColorTertiaryContainer
val Light_OnTertiaryContainer     = ColorOnTertiaryContainer
val Light_Error                   = ColorError
val Light_OnError                 = ColorOnError
val Light_ErrorContainer          = ColorErrorContainer
val Light_OnErrorContainer        = ColorOnErrorContainer
val Light_SurfaceTint             = ColorSurfaceTint

// =============================================================
// Semantic aliases — backward-compatible names used across the app
// =============================================================

val superMainColor          = ColorPrimaryContainer    // vivid violet #7860E8
val primaryGradientStart: Color = Color(0xFF9C6FFF)    // bright violet
val primaryGradientEnd: Color   = Color(0xFF6A40E8)    // deep violet
val progressBackground: Color   = ColorSurfaceContainerHigh

val badgeNew: Brush = Brush.horizontalGradient(
    colors = listOf(ColorPrimaryContainer, ColorPrimary.copy(alpha = 0.6f))
)
val badgeComplete: Brush = Brush.horizontalGradient(
    colors = listOf(Color(0xFF10B981), Color(0xFF059669))
)

// Legacy aliases
val BluePrimary             = ColorPrimary
val BluePrimaryContainer    = ColorPrimaryContainer
val BlueOnPrimary           = ColorOnPrimary
val BlueOnPrimaryContainer  = ColorOnPrimaryContainer
val Secondary               = ColorSecondary
val Error                   = ColorError
val OnError                 = ColorOnError
