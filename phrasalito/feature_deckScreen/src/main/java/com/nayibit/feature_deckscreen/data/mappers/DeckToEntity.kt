package com.nayibit.feature_deckscreen.data.mappers

import com.nayibit.database.room.entities.DeckEntity
import com.nayibit.feature_deckscreen.domain.model.Deck

fun Deck.toEntity() = DeckEntity(
    idCategory = idCategory,
    name =  name,
    lngCode = lngCode,
    languageName= languageName,
    isNotified = isNotified)