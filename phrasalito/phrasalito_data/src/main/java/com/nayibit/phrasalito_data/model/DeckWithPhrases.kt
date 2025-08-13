package com.nayibit.phrasalito_data.model

import androidx.room.Embedded
import androidx.room.Relation
import com.nayibit.phrasalito_data.entities.DeckEntity
import com.nayibit.phrasalito_data.entities.PhraseEntity
import com.nayibit.phrasalito_domain.model.Deck

data class DeckWithPhrases(
    @Embedded val deck: DeckEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "deckId"
    )
    val phrases: List<PhraseEntity>
){
    fun toDomain(): Deck {
        return Deck(
            id = deck.id,
            name = deck.name,
            maxCards = phrases.size)
    }
}
