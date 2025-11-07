package com.nayibit.phrasalito_presentation.model

import androidx.compose.ui.geometry.Rect
import com.nayibit.phrasalito_presentation.utils.LabelPosition

data class TutorialStep(
    val rect: Rect = Rect.Zero,
    val description: String = "",
    val labelPosition: LabelPosition
)
