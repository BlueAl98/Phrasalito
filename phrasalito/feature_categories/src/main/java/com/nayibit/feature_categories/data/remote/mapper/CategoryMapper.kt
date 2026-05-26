package com.nayibit.feature_categories.data.remote.mapper

import com.nayibit.database.room.entities.CategoryEntity
import com.nayibit.feature_categories.data.remote.dto.CategoryDto

fun CategoryDto.toEntity() = CategoryEntity(
    id = id,
    name = name,
    subtitle = subtitle,
    maxDecks = numDecks,
    languageId = languageId,
    icon = icon,
    isDefault = true
)
