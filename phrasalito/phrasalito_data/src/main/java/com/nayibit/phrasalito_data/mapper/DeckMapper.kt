package com.nayibit.phrasalito_data.mapper

import com.nayibit.phrasalito_data.entities.DeckEntity
import com.nayibit.phrasalito_data.model.DeckWithPhrases
import com.nayibit.phrasalito_domain.model.Deck

fun DeckEntity.toDomain(): Deck {
    return Deck(
            id = id,
            name = name,
            maxCards = maxCards)
}

fun Deck.toEntity(): DeckEntity {
    return DeckEntity(
        id = id,
        name = name,
        maxCards = maxCards)
}

fun DeckWithPhrases.toDomain(): Deck {
   return Deck(
        id = deck.id,
        name = deck.name,
        maxCards = phrases.size)
}