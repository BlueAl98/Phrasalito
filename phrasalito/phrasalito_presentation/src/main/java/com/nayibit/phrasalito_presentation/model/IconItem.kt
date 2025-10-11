package com.nayibit.phrasalito_presentation.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class IconItem(
    val icon: ImageVector,
    val title: String,
    val color: Color,
    val onClick: () -> Unit = {},
    val enabled: Boolean = true
)
