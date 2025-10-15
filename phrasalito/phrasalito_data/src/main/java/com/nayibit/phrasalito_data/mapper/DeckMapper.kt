package com.nayibit.phrasalito_data.mapper

import com.nayibit.phrasalito_data.entities.DeckEntity
import com.nayibit.phrasalito_data.model.DeckWithPhrases
import com.nayibit.phrasalito_domain.model.Deck

fun DeckEntity.toDomain(): Deck {
    return Deck(
            id = id,
            name = name,
            maxCards = maxCards,
            lngCode = lngCode ,
            languageName = languageName
        )
}

fun Deck.toEntity(): DeckEntity {
    return DeckEntity(
        id = id,
        name = name,
        maxCards = maxCards,
        lngCode = lngCode ,
        languageName = languageName
        )
}

fun DeckWithPhrases.toDomain(): Deck {
   return Deck(
        id = deck.id,
        name = deck.name,
        maxCards = phrases.size,
       lngCode = deck.lngCode ,
       languageName = deck.languageName
       )
}