package com.nayibit.feature_categories.presentation.model

import androidx.compose.ui.graphics.vector.ImageVector


data class CategoryUi(
    val id: Int,
    val title: String,
    val subtitle: String = "",
    val progress: Float, // 0f - 1f
    val icon: ImageVector,
    val isFlipped: Boolean = false
    )

enum class TypeModal {
    CREATE,
    UPDATE,
    DELETE
}