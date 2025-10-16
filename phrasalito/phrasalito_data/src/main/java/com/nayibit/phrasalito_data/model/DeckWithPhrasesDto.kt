package com.nayibit.phrasalito_data.model

import androidx.room.Embedded
import androidx.room.Relation
import com.nayibit.phrasalito_data.entities.DeckEntity
import com.nayibit.phrasalito_data.entities.PhraseEntity

data class DeckWithPhrasesDto(
    @Embedded val deck: DeckEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "deckId"
    )
    val phrases: List<PhraseEntity>
)