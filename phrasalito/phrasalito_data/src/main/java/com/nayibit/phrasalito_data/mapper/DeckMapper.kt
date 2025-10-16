package com.nayibit.phrasalito_data.mapper

import com.nayibit.phrasalito_data.entities.DeckEntity
import com.nayibit.phrasalito_data.model.DeckWithPhrasesDto
import com.nayibit.phrasalito_domain.model.Deck
import com.nayibit.phrasalito_domain.model.DeckWithPhrases

fun DeckEntity.toDeck(): Deck {
    return Deck(
            id = id,
            name = name,
            maxCards = maxCards,
            lngCode = lngCode ,
            languageName = languageName,
            isNotified = isNotified
        )
}

fun Deck.toEntity(): DeckEntity {
    return DeckEntity(
        id = id,
        name = name,
        maxCards = maxCards,
        lngCode = lngCode ,
        languageName = languageName,
        isNotified = isNotified
        )
}

fun DeckWithPhrasesDto.toPhrase(): Deck {
   return Deck(
        id = deck.id,
        name = deck.name,
        maxCards = phrases.size,
        lngCode = deck.lngCode ,
        languageName = deck.languageName,
        isNotified = deck.isNotified
       )
}

fun DeckWithPhrasesDto.toDomain(): DeckWithPhrases {
    return DeckWithPhrases(
        deck = deck.toDeck(),
        phrases = phrases.map { it.toPhrase() }
    )

}