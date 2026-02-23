package com.nayibit.feature_categories.data.mappers

import com.nayibit.database.room.entities.CategoryEntity
import com.nayibit.feature_categories.model.Category

fun Category.toEntity() =
    CategoryEntity(
        name = name,
        subtitle = subtitle,
    )