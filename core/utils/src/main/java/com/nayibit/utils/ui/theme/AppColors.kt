package com.nayibit.utils.ui.theme

import androidx.compose.ui.graphics.Color
import com.nayibit.utils.ui.model.LearningColors

fun learningColors(
    primary: Color = Dark_Primary,
    textPrimary: Color = Dark_OnSurface
) = LearningColors(
    background    = primary.copy(alpha = 0.08f),
    card          = Dark_SurfaceContainerLow,
    cardBorder    = primary.copy(alpha = 0.25f),
    primary       = primary,
    textPrimary   = textPrimary,
    textSecondary = Dark_OnSurfaceVariant,
    progressTrack = primary.copy(alpha = 0.18f),
    badge         = primary.copy(alpha = 0.22f)
)
