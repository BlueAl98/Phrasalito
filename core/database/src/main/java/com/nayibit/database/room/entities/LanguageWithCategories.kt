package com.nayibit.database.room.entities

import androidx.room.Embedded
import androidx.room.Relation

data class LanguageWithCategories(
    @Embedded
    val language: LanguageEntity,

    @Relation(
        entity = CategoryEntity::class,
        parentColumn = "id",
        entityColumn = "languageId"
    )
    val categories: List<CategoryWithDeckEntity>
)
