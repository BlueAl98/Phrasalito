package com.nayibit.utils.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.nayibit.utils.ui.model.LearningColors

@Composable
fun learningColors(
    primary: Color = MaterialTheme.colorScheme.primary,
    textPrimary: Color = MaterialTheme.colorScheme.onSurface
): LearningColors = LearningColors(
    background    = primary.copy(alpha = 0.08f),
    card          = MaterialTheme.colorScheme.surfaceContainerLow,
    cardBorder    = primary.copy(alpha = 0.25f),
    primary       = primary,
    textPrimary   = textPrimary,
    textSecondary = MaterialTheme.colorScheme.onSurfaceVariant,
    progressTrack = primary.copy(alpha = 0.18f),
    badge         = primary.copy(alpha = 0.22f)
)
