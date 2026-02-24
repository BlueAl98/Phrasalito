package com.nayibit.utils.ui.theme

import androidx.compose.ui.graphics.Color
import com.nayibit.utils.ui.model.LearningColors

fun learningColors(primary: Color = Color(0xFF0047AB), textPrimary: Color = Color.White) = LearningColors(
    background = primary.copy(alpha = 0.08f),
    card = primary.copy(alpha = 0.14f),
    cardBorder = primary.copy(alpha = 0.25f),
    primary = primary,
    textPrimary = textPrimary,
    textSecondary = Color(0xFFB0B8C9),
    progressTrack = primary.copy(alpha = 0.18f),
    badge = primary.copy(alpha = 0.22f)
)
