package com.nayibit.feature_deckscreen.data.mappers

import com.nayibit.database.room.entities.DeckEntity
import com.nayibit.feature_deckscreen.domain.model.Deck

fun DeckEntity.toDeck() = Deck(
    id = id,
    idCategory = idCategory,
    name = name,
    maxCards = maxCards,
    lngCode = lngCode,
    languageName = languageName,
    isNotified = isNotified
)