package com.nayibit.utils.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

// =============================================================
// DESIGN SYSTEM: Modern Linguistic
// Single source of truth for all app colors.
// Change values here to update the entire app.
// =============================================================

// --- Dark scheme (from DESIGN.md) ---
val Dark_Background              = Color(0xFF131313)
val Dark_Surface                 = Color(0xFF131313)
val Dark_SurfaceDim              = Color(0xFF131313)
val Dark_SurfaceBright           = Color(0xFF393939)
val Dark_SurfaceContainerLowest  = Color(0xFF0E0E0E)
val Dark_SurfaceContainerLow     = Color(0xFF1B1B1B)
val Dark_SurfaceContainer        = Color(0xFF1F1F1F)
val Dark_SurfaceContainerHigh    = Color(0xFF2A2A2A)
val Dark_SurfaceContainerHighest = Color(0xFF353535)
val Dark_SurfaceVariant          = Color(0xFF353535)
val Dark_OnSurface               = Color(0xFFE2E2E2)
val Dark_OnSurfaceVariant        = Color(0xFFC2C6D6)
val Dark_InverseSurface          = Color(0xFFE2E2E2)
val Dark_InverseOnSurface        = Color(0xFF303030)
val Dark_Outline                 = Color(0xFF8C909F)
val Dark_OutlineVariant          = Color(0xFF424654)
val Dark_Primary                 = Color(0xFFB0C6FF)
val Dark_OnPrimary               = Color(0xFF002D6E)
val Dark_PrimaryContainer        = Color(0xFF1D66DB)
val Dark_OnPrimaryContainer      = Color(0xFFEAEDFF)
val Dark_InversePrimary          = Color(0xFF0058CA)
val Dark_Secondary               = Color(0xFFBEC6DF)
val Dark_OnSecondary             = Color(0xFF283044)
val Dark_SecondaryContainer      = Color(0xFF41495E)
val Dark_OnSecondaryContainer    = Color(0xFFB0B8D1)
val Dark_Tertiary                = Color(0xFF7CD0FF)
val Dark_OnTertiary              = Color(0xFF00354A)
val Dark_TertiaryContainer       = Color(0xFF00749D)
val Dark_OnTertiaryContainer     = Color(0xFFDEF1FF)
val Dark_Error                   = Color(0xFFFFB4AB)
val Dark_OnError                 = Color(0xFF690005)
val Dark_ErrorContainer          = Color(0xFF93000A)
val Dark_OnErrorContainer        = Color(0xFFFFDAD6)
val Dark_SurfaceTint             = Color(0xFFB0C6FF)

// --- Light scheme (derived from DESIGN.md fixed/inverse tokens) ---
val Light_Background              = Color(0xFFFAFAFF)
val Light_Surface                 = Color(0xFFFAFAFF)
val Light_SurfaceDim              = Color(0xFFDADAE4)
val Light_SurfaceBright           = Color(0xFFFAFAFF)
val Light_SurfaceContainerLowest  = Color(0xFFFFFFFF)
val Light_SurfaceContainerLow     = Color(0xFFF5F5FF)
val Light_SurfaceContainer        = Color(0xFFEFEFFA)
val Light_SurfaceContainerHigh    = Color(0xFFE9E9F4)
val Light_SurfaceContainerHighest = Color(0xFFE3E3EE)
val Light_SurfaceVariant          = Color(0xFFE3E3EF)
val Light_OnSurface               = Color(0xFF1A1C20)
val Light_OnSurfaceVariant        = Color(0xFF45474F)
val Light_InverseSurface          = Color(0xFF2F3033)
val Light_InverseOnSurface        = Color(0xFFF1F0F4)
val Light_Outline                 = Color(0xFF767780)
val Light_OutlineVariant          = Color(0xFFC6C6D3)
val Light_Primary                 = Color(0xFF0058CA)   // Dark_InversePrimary
val Light_OnPrimary               = Color.White
val Light_PrimaryContainer        = Color(0xFFD9E2FF)   // primary-fixed
val Light_OnPrimaryContainer      = Color(0xFF001944)   // on-primary-fixed
val Light_InversePrimary          = Color(0xFFB0C6FF)   // Dark_Primary
val Light_Secondary               = Color(0xFF3F465B)   // on-secondary-fixed-variant
val Light_OnSecondary             = Color.White
val Light_SecondaryContainer      = Color(0xFFDAE2FC)   // secondary-fixed
val Light_OnSecondaryContainer    = Color(0xFF131B2E)   // on-secondary-fixed
val Light_Tertiary                = Color(0xFF004C69)   // on-tertiary-fixed-variant
val Light_OnTertiary              = Color.White
val Light_TertiaryContainer       = Color(0xFFC4E7FF)   // tertiary-fixed
val Light_OnTertiaryContainer     = Color(0xFF001E2C)   // on-tertiary-fixed
val Light_Error                   = Color(0xFFBA1A1A)
val Light_OnError                 = Color.White
val Light_ErrorContainer          = Color(0xFFFFDAD6)
val Light_OnErrorContainer        = Color(0xFF410002)
val Light_SurfaceTint             = Color(0xFF0058CA)

// =============================================================
// Semantic aliases — used throughout the app by name.
// These keep backward-compatibility with existing composables.
// =============================================================

/** Main brand primary (adapts per theme; use via MaterialTheme.colorScheme.primary instead). */
val superMainColor          = Dark_Primary

/** Gradient pair for interactive elements and illustrations. */
val primaryGradientStart: Color = Dark_Tertiary          // light blue
val primaryGradientEnd: Color   = Dark_PrimaryContainer  // action blue

/** Progress bar empty track. */
val progressBackground: Color = Dark_SurfaceContainerHigh

/** Badge brushes */
val badgeNew: Brush = Brush.horizontalGradient(
    colors = listOf(Dark_Primary, Dark_Primary.copy(alpha = 0.5f))
)
val badgeComplete: Brush = Brush.horizontalGradient(
    colors = listOf(Color(0xFF10B981), Color(0xFF059669))
)

// Legacy aliases (kept so existing imports compile without changes)
val BluePrimary             = Dark_Primary
val BluePrimaryContainer    = Dark_PrimaryContainer
val BlueOnPrimary           = Dark_OnPrimary
val BlueOnPrimaryContainer  = Dark_OnPrimaryContainer
val Secondary               = Dark_Secondary
val Error                   = Dark_Error
val OnError                 = Dark_OnError
