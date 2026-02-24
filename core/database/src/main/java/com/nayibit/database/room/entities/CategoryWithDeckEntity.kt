package com.nayibit.database.room.entities

import androidx.room.Embedded
import androidx.room.Relation

data class CategoryWithDeckEntity(
    @Embedded
    val category: CategoryEntity,

    @Relation(
        entity = DeckEntity::class,
        parentColumn = "id",
        entityColumn = "idCategory"
    )
    val decks: List<DeckWithPhrases>
)
