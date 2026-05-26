package com.nayibit.feature_categories.presentation.model

data class CategoryUi(
    val id: Int,
    val title: String,
    val subtitle: String = "",
    val progress: Float,
    val iconEmoji: String = "",
    val isDefault: Boolean = false,
    val isFlipped: Boolean = false
)

enum class TypeModal {
    CREATE,
    UPDATE,
    DELETE
}
