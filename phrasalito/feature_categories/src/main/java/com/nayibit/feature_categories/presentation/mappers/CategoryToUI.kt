package com.nayibit.feature_categories.presentation.mappers

import com.nayibit.feature_categories.model.Category
import com.nayibit.feature_categories.presentation.model.CategoryUi

fun Category.toUI() = CategoryUi(
    id = id,
    title = name,
    subtitle = subtitle,
    progress = progress,
    iconEmoji = icon,
    isDefault = isDefault
)

fun CategoryUi.toDomain() = Category(
    id = id,
    name = title,
    subtitle = subtitle,
    icon = iconEmoji,
    isDefault = isDefault,
    languageId = 0
)
