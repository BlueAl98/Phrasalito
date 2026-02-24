package com.nayibit.feature_deckscreen.presentation.model

import androidx.compose.ui.geometry.Rect
import com.nayibit.utils.helpers.LabelPosition

data class TutorialStep(
    val rect: Rect = Rect.Zero,
    val description: String = "",
    val labelPosition: LabelPosition
)
