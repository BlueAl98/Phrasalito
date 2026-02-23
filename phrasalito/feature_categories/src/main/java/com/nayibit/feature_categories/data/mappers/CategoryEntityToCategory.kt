package com.nayibit.feature_categories.data.mappers

import com.nayibit.database.room.entities.CategoryWithDeckEntity
import com.nayibit.feature_categories.model.Category

fun CategoryWithDeckEntity.toCategory() = Category(
    id = category.id,
    name = category.name,
    subtitle = category.subtitle,
    maxDecks = category.maxDecks,
    currentDecks = decks.size,
    progress = if (category.maxDecks > 0) decks.size.toFloat() / category.maxDecks.toFloat() else 0f
)
