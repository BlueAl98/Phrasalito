package com.nayibit.database.room.entities

import androidx.room.Embedded
import androidx.room.Relation

data class DeckWithPhrases(
    @Embedded
    val deck: DeckEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "deckId"
    )
    val phrases: List<PhraseEntity>
)